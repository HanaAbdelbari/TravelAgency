package notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import notifications.model.Notification;
import java.util.List;
import java.util.Map;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByStatusAndMessageType(String status, String type);
    
    @Query("SELECT n.recipient, COUNT(n) FROM Notification n GROUP BY n.recipient ORDER BY COUNT(n) DESC")
    List<Object[]> findMostNotifiedRecipients();

    @Query("SELECT n.messageType, COUNT(n) FROM Notification n GROUP BY n.messageType")
    Map<String, Long> countByTypes();

    @Query("SELECT n.failureReason, COUNT(n) FROM Notification n WHERE n.status = 'FAILED' GROUP BY n.failureReason ORDER BY COUNT(n) DESC")
    List<String> findTopFailureReasons();
}
