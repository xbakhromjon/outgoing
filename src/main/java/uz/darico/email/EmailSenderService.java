package uz.darico.email;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.darico.email.dto.EmailSenderDTO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 07/10/22, Fri, 13:28
 **/
@Service
public class EmailSenderService {
    public boolean send(EmailSenderDTO emailSenderDTO) {
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("xbakhromjon@gmail.com", "pgtqttrbaalmufnd");
            }
        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailSenderDTO.getFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailSenderDTO.getTo()));
            message.setSubject(emailSenderDTO.getSubject());
            if (emailSenderDTO.getText() != null) {
                message.setText(emailSenderDTO.getText());
            }
            // html
//            message.setContent(
//                    emailSenderDTO.getContentHtml(),
//                    "text/html");

            // attachfile
            if (emailSenderDTO.getFilePath() != null) {
                Multipart multipart = new MimeMultipart();
                MimeBodyPart attachmentPart = new MimeBodyPart();
                MimeBodyPart textPart = new MimeBodyPart();
                try {
                    File f = new File(emailSenderDTO.getFilePath());
                    attachmentPart.attachFile(f);
                    textPart.setText("This is text");
                    multipart.addBodyPart(textPart);
                    multipart.addBodyPart(attachmentPart);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                message.setContent(multipart);
            }
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return true;
    }
}
