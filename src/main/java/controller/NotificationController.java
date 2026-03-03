package controller;
import model.Notification;

//import com.sun.nio.sctp.Notification;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import model.DeliveryPersonnel;

import java.util.*;
import java.text.SimpleDateFormat;

public class NotificationController {

    private List<DeliveryPersonnel> deliveryPersonnelList;
    private List<Notification> notificationHistory;

    public NotificationController() {
        this.deliveryPersonnelList = new ArrayList<>();
        this.notificationHistory = new ArrayList<>();
        // Sample delivery personnel - replace emails with real addresses for testing
        deliveryPersonnelList.add(new DeliveryPersonnel("DP001", "Poojah", "kpuvajini@gmail.com"));
        deliveryPersonnelList.add(new DeliveryPersonnel("DP002", "Sharan", "gowthamansharan0@gmail.com"));
    }

    public boolean sendNotification(String deliveryPersonId, String message) {
        DeliveryPersonnel dp = findDeliveryPersonById(deliveryPersonId);
        if (dp == null) {
            System.out.println("Delivery personnel not found.");
            return false;
        }

        boolean emailSent = sendEmail(dp.getEmail(), "FastTrack Logistics Notification", message);

        if (emailSent) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Notification notification = new Notification(deliveryPersonId, message, timestamp);
            notificationHistory.add(notification);
            System.out.println("Notification sent to " + dp.getName() + " (" + dp.getEmail() + ")");
            return true;
        } else {
            System.out.println("Failed to send email.");
            return false;
        }
    }


    private DeliveryPersonnel findDeliveryPersonById(String id) {
        for (DeliveryPersonnel dp : deliveryPersonnelList) {
            if (dp.getId().equals(id)) {
                return dp;
            }
        }
        return null;
    }

    private boolean sendEmail(String toEmail, String subject, String body) {
        final String fromEmail = "kpuvajini@gmail.com";       // Change this to your Gmail
        final String password = "hgdrwzprvrzlptxh";            // Your Gmail App Password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");          // SMTP Host
        props.put("mail.smtp.port", "587");                     // TLS Port
        props.put("mail.smtp.auth", "true");                    // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");         // Enable STARTTLS

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail, "FastTrack Logistics"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setSentDate(new Date());

            Transport.send(msg);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notification> getNotificationHistory() {
        return notificationHistory;
    }
}
