package tn.esprit.services;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class MailingService {
    final String username;
    final String password;
    Properties props;
    Session session;
    public MailingService() throws IOException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream("src/main/resources/config.properties") ;
        properties.load(input);
        // Accessing properties
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com"); // Outlook SMTP server
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });
    }


    public void sendBannedProfileNotification(String destination) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            message.setSubject("Recruit Profile Banned");

            String htmlContent = "<html>" +
                    "<body>" +
                    "<h1>Your profile has been banned for 3 days</h1>" +
                    "<p>Dear user,</p>" +
                    "<p>Your profile has been banned for 3 days due to multiple violations of our community guidelines. This action was taken because you have posted content containing inappropriate language multiple times. During this ban period, you are not allowed to post any content.</p>" +
                    "<p>Thank you for your understanding.</p>" +
                    "</body>" +
                    "</html>";

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
