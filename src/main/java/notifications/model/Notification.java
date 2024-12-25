package notifications.model;

import java.time.LocalDateTime;

public class Notification {
    private String type;
    private String recipient;
    private String content;
    private boolean isSent;
    private String status;
    private LocalDateTime sentAt;
    private String failureReason;

    public Notification(String type, String recipient, String content) {
        this.type = type;
        this.recipient = recipient;
        this.content = content;
        this.status = "PENDING";
    }

    public String getType() { return type; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public boolean isSent() { return isSent; }
    public void setSent(boolean sent) { isSent = sent; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public LocalDateTime getSentAt() { return sentAt; }
}
