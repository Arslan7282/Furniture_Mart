package com.example.finalyearproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.example.com"); // Use your SMTP server
            props.put("mail.smtp.port", "587"); // Use your SMTP server port
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");



            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("your_email@example.com", "your_email_password");
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("user_email"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("arslanaslam7282@gmail.com"));
            message.setSubject("New Order Placed");
            message.setText("A new order has been placed. Please check your admin panel for details.");

            Transport.send(message);

            return true;
        } catch (Exception e) {
            Log.e("SendEmailTask", "Error sending email", e);
            return false;
        }
    }
}
