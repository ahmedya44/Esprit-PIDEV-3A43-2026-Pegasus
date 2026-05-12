package com.pegasus.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pegasus.config.EnvLoader;
import com.pegasus.config.PropertiesLoader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GoogleAuthService {
    private static final String CONFIG_PATH = "/google-oauth.properties";
    private static final String AUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String USERINFO_ENDPOINT = "https://openidconnect.googleapis.com/v1/userinfo";
    private static final Duration HTTP_TIMEOUT = Duration.ofSeconds(30);
    private static final int CALLBACK_TIMEOUT_SECONDS = 180;
    private static final String SUCCESS_HTML = """
            <html><body style="font-family: Arial, sans-serif; padding: 24px;">
            <h2>Google sign-in completed</h2>
            <p>You can close this browser tab and return to Pegasus.</p>
            </body></html>
            """;
    private static final String ERROR_HTML = """
            <html><body style="font-family: Arial, sans-serif; padding: 24px;">
            <h2>Google sign-in failed</h2>
            <p>You can close this browser tab and return to Pegasus.</p>
            </body></html>
            """;

    private final HttpClient httpClient;
    private final Gson gson;
    private final OAuthConfig config;

    public GoogleAuthService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(HTTP_TIMEOUT)
                .build();
        this.gson = new Gson();
        this.config = loadConfig();
    }

    public GoogleUserProfile signIn() {
        String state = generateRandomUrlSafeValue(32);
        String codeVerifier = generateRandomUrlSafeValue(64);
        String codeChallenge = createCodeChallenge(codeVerifier);

        CallbackResult callbackResult = waitForAuthorizationCode(state, codeVerifier, codeChallenge);
        TokenResponse tokenResponse = exchangeCodeForTokens(callbackResult.authorizationCode(), codeVerifier);
        return fetchUserProfile(tokenResponse.accessToken());
    }

    private CallbackResult waitForAuthorizationCode(String expectedState, String codeVerifier, String codeChallenge) {
        URI redirectUri = URI.create(config.redirectUri());
        InetSocketAddress callbackAddress = new InetSocketAddress(redirectUri.getHost(), resolvePort(redirectUri));
        CountDownLatch latch = new CountDownLatch(1);
        AuthCallbackHolder holder = new AuthCallbackHolder();

        HttpServer server;
        try {
            server = HttpServer.create(callbackAddress, 0);
        } catch (IOException e) {
            throw new IllegalStateException("Could not start Google callback server on " + config.redirectUri(), e);
        }

        server.createContext(redirectUri.getPath(), exchange -> handleCallbackRequest(exchange, holder, latch));
        server.setExecutor(null);
        server.start();

        try {
            openBrowser(buildAuthorizationUri(expectedState, codeChallenge));
            boolean completed = latch.await(CALLBACK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!completed) {
                throw new IllegalStateException("Google sign-in timed out.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Google sign-in was interrupted.", e);
        } finally {
            server.stop(0);
        }

        if (holder.error != null) {
            throw new IllegalStateException("Google sign-in failed: " + holder.error);
        }
        if (holder.authorizationCode == null) {
            throw new IllegalStateException("Google sign-in did not return an authorization code.");
        }
        if (!expectedState.equals(holder.state)) {
            throw new IllegalStateException("Google sign-in failed because the returned state was invalid.");
        }

        return new CallbackResult(holder.authorizationCode);
    }

    private void handleCallbackRequest(HttpExchange exchange, AuthCallbackHolder holder, CountDownLatch latch) throws IOException {
        try {
            Map<String, String> query = parseQuery(exchange.getRequestURI().getRawQuery());
            holder.authorizationCode = query.get("code");
            holder.state = query.get("state");
            holder.error = query.get("error");

            String response = holder.error == null ? SUCCESS_HTML : ERROR_HTML;
            byte[] body = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(body);
            }
        } finally {
            exchange.close();
            latch.countDown();
        }
    }

    private URI buildAuthorizationUri(String state, String codeChallenge) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", config.clientId());
        params.put("redirect_uri", config.redirectUri());
        params.put("response_type", "code");
        params.put("scope", "openid email profile");
        params.put("access_type", "offline");
        params.put("prompt", "select_account");
        params.put("state", state);
        params.put("code_challenge", codeChallenge);
        params.put("code_challenge_method", "S256");
        return URI.create(AUTH_ENDPOINT + "?" + buildFormData(params));
    }

    private TokenResponse exchangeCodeForTokens(String authorizationCode, String codeVerifier) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", config.clientId());
        params.put("client_secret", config.clientSecret());
        params.put("code", authorizationCode);
        params.put("code_verifier", codeVerifier);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", config.redirectUri());

        HttpRequest request = HttpRequest.newBuilder(URI.create(TOKEN_ENDPOINT))
                .timeout(HTTP_TIMEOUT)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(buildFormData(params)))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("Google token exchange failed: " + response.body());
            }

            JsonObject json = gson.fromJson(response.body(), JsonObject.class);
            String accessToken = getAsString(json, "access_token");
            if (accessToken == null) {
                throw new IllegalStateException("Google token response did not include an access token.");
            }

            return new TokenResponse(accessToken, getAsString(json, "id_token"));
        } catch (IOException e) {
            throw new IllegalStateException("Could not contact Google token endpoint.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Google token exchange was interrupted.", e);
        }
    }

    private GoogleUserProfile fetchUserProfile(String accessToken) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(USERINFO_ENDPOINT))
                .timeout(HTTP_TIMEOUT)
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("Google userinfo request failed: " + response.body());
            }

            JsonObject json = gson.fromJson(response.body(), JsonObject.class);
            String sub = getAsString(json, "sub");
            String email = getAsString(json, "email");
            if (sub == null || email == null) {
                throw new IllegalStateException("Google userinfo response was missing required fields.");
            }

            return new GoogleUserProfile(
                    sub,
                    email,
                    getAsBoolean(json, "email_verified"),
                    getAsString(json, "name"),
                    getAsString(json, "given_name"),
                    getAsString(json, "family_name"),
                    getAsString(json, "picture")
            );
        } catch (IOException e) {
            throw new IllegalStateException("Could not contact Google userinfo endpoint.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Google userinfo request was interrupted.", e);
        }
    }

    private void openBrowser(URI uri) {
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            throw new IllegalStateException("Desktop browsing is not supported on this machine.");
        }
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            throw new IllegalStateException("Could not open the browser for Google sign-in.", e);
        }
    }

    private OAuthConfig loadConfig() {
        Properties properties = PropertiesLoader.load(CONFIG_PATH, GoogleAuthService.class);

        String clientId = readValue(properties, "google.clientId", "GOOGLE_CLIENT_ID");
        String clientSecret = readValue(properties, "google.clientSecret", "GOOGLE_CLIENT_SECRET");
        String redirectUri = readValue(properties, "google.redirectUri", "GOOGLE_REDIRECT_URI");

        if (clientId == null || clientSecret == null || redirectUri == null) {
            throw new IllegalStateException("Google OAuth config is incomplete.");
        }

        if (clientId.startsWith("YOUR_") || clientSecret.startsWith("YOUR_")) {
            throw new IllegalStateException("Replace the placeholder Google OAuth values in google-oauth.properties.");
        }

        return new OAuthConfig(clientId, clientSecret, redirectUri);
    }

    private String generateRandomUrlSafeValue(int byteCount) {
        byte[] buffer = new byte[byteCount];
        new SecureRandom().nextBytes(buffer);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
    }

    private String readValue(Properties properties, String propertyKey, String envKey) {
        String value = trimToNull(properties.getProperty(propertyKey));
        if (value != null) {
            return value;
        }
        return trimToNull(EnvLoader.get(envKey));
    }

    private String createCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available on this JVM.", e);
        }
    }

    private Map<String, String> parseQuery(String rawQuery) {
        Map<String, String> params = new LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return params;
        }

        for (String pair : rawQuery.split("&")) {
            if (pair.isBlank()) {
                continue;
            }
            String[] pieces = pair.split("=", 2);
            String key = urlDecode(pieces[0]);
            String value = pieces.length > 1 ? urlDecode(pieces[1]) : "";
            params.put(key, value);
        }
        return params;
    }

    private String buildFormData(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!first) {
                builder.append('&');
            }
            builder.append(urlEncode(entry.getKey()));
            builder.append('=');
            builder.append(urlEncode(entry.getValue()));
            first = false;
        }
        return builder.toString();
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String urlDecode(String value) {
        return java.net.URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private int resolvePort(URI uri) {
        if (uri.getPort() != -1) {
            return uri.getPort();
        }
        if ("https".equalsIgnoreCase(uri.getScheme())) {
            return 443;
        }
        return 80;
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private String getAsString(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
    }

    private boolean getAsBoolean(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() && json.get(key).getAsBoolean();
    }

    private record OAuthConfig(String clientId, String clientSecret, String redirectUri) {
    }

    private record CallbackResult(String authorizationCode) {
    }

    private record TokenResponse(String accessToken, String idToken) {
    }

    private static final class AuthCallbackHolder {
        private String authorizationCode;
        private String state;
        private String error;
    }
}
