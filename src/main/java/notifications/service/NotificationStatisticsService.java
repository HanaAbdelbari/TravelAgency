package notifications.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import notifications.repository.NotificationRepository;

@Service
public class NotificationStatisticsService {

    @Autowired
    private NotificationRepository notificationRepository;

    public long countByStatusAndType(String status, String type) {
        return notificationRepository.countByStatusAndMessageType(status, type);
    }

    public List<Object[]> mostNotifiedRecipients() {
        return notificationRepository.findMostNotifiedRecipients();
    }

    public Map<String, Long> getNotificationCountsByType() {
        return notificationRepository.countByTypes();
    }

    public List<String> getTopFailureReasons() {
        return notificationRepository.findTopFailureReasons();
    }
}
