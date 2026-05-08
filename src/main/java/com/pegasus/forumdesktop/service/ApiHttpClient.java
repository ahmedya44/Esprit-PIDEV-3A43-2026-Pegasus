package com.pegasus.forumdesktop.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

public class ApiHttpClient {
    private final CloseableHttpClient httpClient;

    public ApiHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(Timeout.ofSeconds(8))
            .setResponseTimeout(Timeout.ofSeconds(10))
            .build();
        this.httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build();
    }

    public String get(String url) throws Exception {
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "application/json");
        request.addHeader("User-Agent", "PegasusForumJavaFX/1.0");
        return httpClient.execute(request, response -> {
            String body = EntityUtils.toString(response.getEntity());
            int status = response.getCode();
            if (status < HttpStatus.SC_SUCCESS || status >= HttpStatus.SC_REDIRECTION) {
                throw new IllegalStateException("API request failed with status " + status + ": " + body);
            }
            return body;
        });
    }

    public String postJson(String url, String json) throws Exception {
        HttpPost request = new HttpPost(url);
        request.addHeader("Accept", "application/json");
        request.addHeader("User-Agent", "PegasusForumJavaFX/1.0");
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        return httpClient.execute(request, response -> {
            String body = EntityUtils.toString(response.getEntity());
            int status = response.getCode();
            if (status < HttpStatus.SC_SUCCESS || status >= HttpStatus.SC_REDIRECTION) {
                throw new IllegalStateException("API request failed with status " + status + ": " + body);
            }
            return body;
        });
    }
}
