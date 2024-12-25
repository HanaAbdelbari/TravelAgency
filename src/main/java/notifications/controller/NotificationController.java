package notifications.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import notifications.service.NotificationService;
import notifications.model.NotificationTemplate;
import notifications.dto.NotificationDTO;
import notifications.dto.BulkNotificationDto;
import notifications.model.Notification;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/register")
    public String registerNotification(@RequestParam String email, @RequestParam String username) {
        String content = NotificationTemplate.getTemplate("REGISTER")
                .replace("{username}", username);
        notificationService.createNotification("REGISTER", email, content);
        return "Registration notification queued.";
    }

    @PostMapping("/reset-password")
    public String resetPasswordNotification(@RequestParam String email, @RequestParam String resetLink) {
        String content = NotificationTemplate.getTemplate("RESET_PASSWORD")
                .replace("{resetLink}", resetLink);
        notificationService.createNotification("RESET_PASSWORD", email, content);
        return "Reset password notification queued.";
    }

    @PostMapping("/hotel-book")
    public String hotelBookNotification(@RequestParam String email, @RequestParam String username,
                                         @RequestParam String hotelName, @RequestParam String checkIn,
                                         @RequestParam String checkOut) {
        String content = NotificationTemplate.getTemplate("HOTEL_BOOK")
                .replace("{username}", username)
                .replace("{hotelName}", hotelName)
                .replace("{checkIn}", checkIn)
                .replace("{checkOut}", checkOut);
        notificationService.createNotification("HOTEL_BOOK", email, content);
        return "Hotel booking notification queued.";
    }

    @PostMapping("/hotel-cancel")
    public String hotelCancelNotification(@RequestParam String email, @RequestParam String username,
                                           @RequestParam String hotelName) {
        String content = NotificationTemplate.getTemplate("HOTEL_CANCEL")
                .replace("{username}", username)
                .replace("{hotelName}", hotelName);
        notificationService.createNotification("HOTEL_CANCEL", email, content);
        return "Hotel cancellation notification queued.";
    }

    @PostMapping("/nearby-events")
    public String nearbyEventsNotification(@RequestParam String email, @RequestParam String username,
                                            @RequestParam String hotelName, @RequestParam String dates) {
        String content = NotificationTemplate.getTemplate("NEARBY_EVENTS")
                .replace("{username}", username)
                .replace("{hotelName}", hotelName)
                .replace("{dates}", dates);
        notificationService.createNotification("NEARBY_EVENTS", email, content);
        return "Nearby events notification queued.";
    }

    @PostMapping("/event-book")
    public String eventBookNotification(@RequestParam String email, @RequestParam String username,
                                         @RequestParam String eventName, @RequestParam String eventDate) {
        String content = NotificationTemplate.getTemplate("EVENT_BOOK")
                .replace("{username}", username)
                .replace("{eventName}", eventName)
                .replace("{eventDate}", eventDate);
        notificationService.createNotification("EVENT_BOOK", email, content);
        return "Event booking notification queued.";
    }

    @PostMapping("/event-cancel")
    public String eventCancelNotification(@RequestParam String email, @RequestParam String username,
                                           @RequestParam String eventName) {
        String content = NotificationTemplate.getTemplate("EVENT_CANCEL")
                .replace("{username}", username)
                .replace("{eventName}", eventName);
        notificationService.createNotification("EVENT_CANCEL", email, content);
        return "Event cancellation notification queued.";
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO dto) {
        Notification notification = notificationService.createNotification(
                dto.getRecipient(), dto.getMessageType(), dto.getMessageContent());
        notificationService.addToQueue(notification);
        return ResponseEntity.ok("Notification queued successfully!");
    }

    @PostMapping("/send-bulk")
    public ResponseEntity<String> sendBulkNotifications(@RequestBody BulkNotificationDto dto) {
        notificationService.sendBulkNotifications(dto.getRecipients(), dto.getMessageType(), dto.getMessageContent());
        return ResponseEntity.ok("Bulk notifications queued successfully!");
    }

    @PostMapping("/process-queue")
    public ResponseEntity<String> processQueue() {
        notificationService.processNotifications();
        return ResponseEntity.ok("Queue processed successfully!");
    }
}
