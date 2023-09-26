package com.example.kaappbai;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailSenderActivity {
    public static void main(String[] args) {
        // Sender's email address and password
        String senderEmail = "youremail@gmail.com";
        String senderPassword = "yourpassword";

        // Recipient's email address
        String recipientEmail = "yotszxc@gmail.com";

        // SMTP server settings
        String host = "smtp.gmail.com";
        int port = 587; // Port for TLS/STARTTLS
        String username = senderEmail;

        // Create a properties object to configure the SMTP settings
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a Session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's and recipient's email addresses
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set the email subject and text
            message.setSubject("Hello, this is a test email");
            message.setText("This is the email content.");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Email sending failed. Error: " + e.getMessage());
        }
    }
}

