package notifications.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import notifications.model.Notification;
import notifications.model.NotificationQueue;
import notifications.repository.NotificationRepository;
import notifications.repository.NotificationQueueRepository;

@Service
public class NotificationQueueHandler {

    @Autowired
    private NotificationQueueRepository queueRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public void processQueue() {
        List<NotificationQueue> queue = queueRepository.findAll();
        for (NotificationQueue item : queue) {
            Notification notification = item.getNotification();
            try {
                sendNotification(notification);
                notification.setStatus("SENT");
                notification.setSentAt(LocalDateTime.now());
                notificationRepository.save(notification);
            } catch (Exception e) {
                notification.setStatus("FAILED");
                notification.setFailureReason(e.getMessage());
                notificationRepository.save(notification);
            }
            queueRepository.delete(item); 
        }
    }

    private void sendNotification(Notification notification) throws Exception {
        System.out.println(" Sending Email to: " + notification.getRecipient());
        System.out.println("Content: " + notification.getContent());
        if (notification.getRecipient().endsWith("@fail.com")) {
            throw new Exception(" failure for email: " + notification.getRecipient());
        }
    }
}

