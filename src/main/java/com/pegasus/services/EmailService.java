package com.pegasus.services;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import com.pegasus.config.EnvLoader;
import com.pegasus.config.PropertiesLoader;

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
        String html = buildBrandedHtml(
                "Verify Your Email",
                "Welcome <strong>" + escapeHtml(displayName) + "</strong>, use this code to activate your Pegasus account.",
                "Verification Code",
                verificationToken,
                "If you did not create this account, you can safely ignore this email."
        );
        sendHtmlEmail(toEmail, subject, html);
    }

    public void sendPasswordResetEmail(String toEmail, String username, String resetToken) {
        String displayName = username == null || username.isBlank() ? "there" : username.trim();
        String subject = "Reset your Pegasus password";
        String html = buildBrandedHtml(
                "Reset Password",
                "Hello <strong>" + escapeHtml(displayName) + "</strong>, we received a request to reset your Pegasus password.",
                "Reset Code",
                resetToken,
                "If you did not request a password reset, you can safely ignore this email."
        );
        sendHtmlEmail(toEmail, subject, html);
    }

    public void sendRoleRequestApprovedEmail(String toEmail, String username, String requestedRole) {
        String displayName = username == null || username.isBlank() ? "there" : username.trim();
        String role = requestedRole == null || requestedRole.isBlank() ? "requested role" : requestedRole.trim();
        String subject = "Role request approved";
        String html = buildBrandedHtml(
                "Request Approved",
                "Hello <strong>" + escapeHtml(displayName) + "</strong>, your request to become <strong>"
                        + escapeHtml(role) + "</strong> has been approved.",
                null,
                null,
                "You can now sign in and use your new role features."
        );
        sendHtmlEmail(toEmail, subject, html);
    }

    public void sendRoleRequestRejectedEmail(String toEmail, String username, String requestedRole, String reason) {
        String displayName = username == null || username.isBlank() ? "there" : username.trim();
        String role = requestedRole == null || requestedRole.isBlank() ? "requested role" : requestedRole.trim();
        String rejectReason = reason == null || reason.isBlank() ? "No reason provided" : reason.trim();
        String subject = "Role request update";
        String html = buildBrandedHtml(
                "Request Rejected",
                "Hello <strong>" + escapeHtml(displayName) + "</strong>, your request to become <strong>"
                        + escapeHtml(role) + "</strong> was rejected.",
                "Reason",
                rejectReason,
                "You can update your information and submit a new request anytime."
        );
        sendHtmlEmail(toEmail, subject, html);
    }

    public void sendPlainEmail(String toEmail, String subject, String body) {
        sendTextEmail(toEmail, subject, body);
    }

    private void sendTextEmail(String toEmail, String subject, String body) {
        sendEmail(toEmail, subject, body, false);
    }

    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        sendEmail(toEmail, subject, htmlBody, true);
    }

    private void sendEmail(String toEmail, String subject, String body, boolean html) {
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
            if (html) {
                message.setContent(body, "text/html; charset=UTF-8");
            } else {
                message.setText(body);
            }
            Transport.send(message);
        } catch (MessagingException e) {
            String detail = e.getMessage();
            if (detail == null || detail.isBlank()) {
                detail = "Unknown SMTP error.";
            }
            throw new IllegalStateException("Could not send email: " + detail, e);
        }
    }

    private String buildBrandedHtml(String title, String introHtml, String keyLabel, String keyValue, String footerText) {
        String codeSection = "";
        if (keyLabel != null && keyValue != null && !keyValue.isBlank()) {
            codeSection = """
                    <div style="margin:20px 0; background:#f5f7ff; border:1px solid #e4e8ff; border-radius:10px; padding:16px;">
                      <div style="font-size:12px; color:#58607a; margin-bottom:8px;">%s</div>
                      <div style="font-size:26px; letter-spacing:2px; font-weight:800; color:#111a3b;">%s</div>
                    </div>
                    """.formatted(escapeHtml(keyLabel), escapeHtml(keyValue));
        }
        return """
                <html>
                <body style="margin:0; padding:0; background:#f2f4fb; font-family:Segoe UI,Arial,sans-serif; color:#1e2438;">
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="padding:26px 12px;">
                    <tr>
                      <td align="center">
                        <table role="presentation" width="620" cellpadding="0" cellspacing="0" style="width:620px; max-width:100%%; background:#ffffff; border-radius:16px; overflow:hidden; border:1px solid #e8ebf6;">
                          <tr>
                            <td style="background:linear-gradient(135deg,#0f1f4d,#223f9a); color:#ffffff; padding:22px 26px;">
                              <div style="font-size:20px; font-weight:800; letter-spacing:.4px;">Pegasus</div>
                              <div style="opacity:.86; margin-top:4px; font-size:13px;">Creative Platform</div>
                            </td>
                          </tr>
                          <tr>
                            <td style="padding:24px 26px 10px 26px;">
                              <div style="font-size:22px; font-weight:800; margin-bottom:12px;">%s</div>
                              <div style="font-size:15px; line-height:1.65; color:#313b5f;">%s</div>
                              %s
                              <div style="font-size:13px; color:#6c7696; margin-top:8px;">%s</div>
                            </td>
                          </tr>
                          <tr>
                            <td style="padding:18px 26px 24px 26px; border-top:1px solid #edf0fa; font-size:12px; color:#8b92ae;">
                              This message was sent automatically by Pegasus.
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(
                escapeHtml(title),
                introHtml == null ? "" : introHtml,
                codeSection,
                escapeHtml(footerText == null ? "" : footerText)
        );
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private MailConfig loadConfig() {
        Properties properties = PropertiesLoader.load(CONFIG_PATH, EmailService.class);

        String host = readValue(properties, "mail.smtp.host", "MAIL_SMTP_HOST");
        String port = readValue(properties, "mail.smtp.port", "MAIL_SMTP_PORT");
        String username = readValue(properties, "mail.smtp.username", "MAIL_SMTP_USERNAME");
        String password = readValue(properties, "mail.smtp.password", "MAIL_SMTP_PASSWORD");
        String from = readValue(properties, "mail.smtp.from", "MAIL_SMTP_FROM");
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

    private String readValue(Properties properties, String propertyKey, String envKey) {
        String value = trimToNull(properties.getProperty(propertyKey));
        if (value != null) {
            return value;
        }
        return trimToNull(EnvLoader.get(envKey));
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
