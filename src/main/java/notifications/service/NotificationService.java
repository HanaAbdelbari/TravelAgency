package notifications.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import notifications.model.Notification;
import java.util.List;

public class NotificationService {
    private final Queue<Notification> notificationQueue = new ConcurrentLinkedQueue<>();

    public Notification createNotification(String type, String recipient, String content) {
        Notification notification = new Notification(type, recipient, content);
        notificationQueue.add(notification);
        System.out.println("Notification added to queue: " + type);
        return notification;
    }

    public void processNotifications() {
        while (!notificationQueue.isEmpty()) {
            Notification notification = notificationQueue.poll();
            try {
                sendEmail(notification);
                notification.setSent(true);
                System.out.println("Notification sent successfully to: " + notification.getRecipient());
            } catch (Exception e) {
                notification.setFailureReason(e.getMessage());
                System.err.println("Failed to send notification: " + e.getMessage());
            }
        }
    }

    private void sendEmail(Notification notification) throws Exception {
        if (Math.random() > 0.9) { // Simulate a 10% failure rate
            throw new Exception("Simulated email failure");
        }
        System.out.println("Mock email sent: " + notification.getContent());
    }

    public void addToQueue(Notification notification) {
        notificationQueue.add(notification);
        System.out.println("Notification added to queue for: " + notification.getRecipient());
    }

    public void sendBulkNotifications(List<String> recipients, String messageType, String messageContent) {
        for (String recipient : recipients) {
            Notification notification = createNotification(messageType, recipient, messageContent);
            addToQueue(notification);
        }
    }
}
