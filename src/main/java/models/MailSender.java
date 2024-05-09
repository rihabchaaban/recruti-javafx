package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    public static void sendEmail(String from, String password, String to, String subject, String body) {

        // Set properties for the email session
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com"); // Outlook SMTP server
        properties.put("mail.smtp.port", "587"); // Port number might need to be adjusted, typically 587

        // Create a new email session with the properties
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Send the email message
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println(e.getMessage());

        }
    }
}
