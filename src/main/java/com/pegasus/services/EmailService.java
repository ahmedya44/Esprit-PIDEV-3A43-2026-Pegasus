package com.pegasus.services;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailService {
    private static final String CONFIG_PATH = "/mail.properties";

    private final MailConfig config;

    public EmailService() {
        this.config = loadConfig();
    }

    public void sendVerificationEmail(String toEmail, String username, String verificationToken) {
        String displayName = username == null || username.isBlank() ? "there" : username.trim();
        String subject = "Verify your Pegasus account";
        String body = """
                Hello %s,

                Welcome to Pegasus.

                Your verification code is:
                %s

                Enter this code in the Pegasus app to activate your account.

                If you did not create this account, you can ignore this email.
                """.formatted(displayName, verificationToken);

        sendTextEmail(toEmail, subject, body);
    }

    public void sendPasswordResetEmail(String toEmail, String username, String resetToken) {
        String displayName = username == null || username.isBlank() ? "there" : username.trim();
        String subject = "Reset your Pegasus password";
        String body = """
                Hello %s,

                We received a request to reset your Pegasus password.

                Your reset code is:
                %s

                Enter this code in the Pegasus app to choose a new password.

                If you did not request a password reset, you can ignore this email.
                """.formatted(displayName, resetToken);

        sendTextEmail(toEmail, subject, body);
    }

    private void sendTextEmail(String toEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", config.host());
        properties.put("mail.smtp.port", config.port());
        properties.put("mail.smtp.auth", Boolean.toString(config.auth()));
        properties.put("mail.smtp.starttls.enable", Boolean.toString(config.startTlsEnabled()));

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.username(), config.password());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.from()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            String detail = e.getMessage();
            if (detail == null || detail.isBlank()) {
                detail = "Unknown SMTP error.";
            }
            throw new IllegalStateException("Could not send verification email: " + detail, e);
        }
    }

    private MailConfig loadConfig() {
        Properties properties = new Properties();
        try (InputStream inputStream = EmailService.class.getResourceAsStream(CONFIG_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("Missing mail config file: " + CONFIG_PATH);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read mail config file.", e);
        }

        String host = trimToNull(properties.getProperty("mail.smtp.host"));
        String port = trimToNull(properties.getProperty("mail.smtp.port"));
        String username = trimToNull(properties.getProperty("mail.smtp.username"));
        String password = trimToNull(properties.getProperty("mail.smtp.password"));
        String from = trimToNull(properties.getProperty("mail.smtp.from"));
        boolean startTlsEnabled = Boolean.parseBoolean(properties.getProperty("mail.smtp.starttls.enable", "true"));
        boolean auth = Boolean.parseBoolean(properties.getProperty("mail.smtp.auth", "true"));

        if (host == null || port == null || username == null || password == null || from == null) {
            throw new IllegalStateException("Mail config is incomplete.");
        }
        if (host.startsWith("YOUR_") || username.startsWith("YOUR_") || password.startsWith("YOUR_") || from.startsWith("YOUR_")) {
            throw new IllegalStateException("Replace the placeholder mail values in mail.properties.");
        }

        return new MailConfig(host, port, username, password, from, startTlsEnabled, auth);
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private record MailConfig(
            String host,
            String port,
            String username,
            String password,
            String from,
            boolean startTlsEnabled,
            boolean auth
    ) {
    }
}
