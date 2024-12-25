package notifications.dto;

import java.util.List;

public class BulkNotificationDto {
    private List<String> recipients;
    private String messageType;
    private String messageContent;

    // Getters and Setters
    public List<String> getRecipients() { return recipients; }
    public void setRecipients(List<String> recipients) { this.recipients = recipients; }
    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
    public String getMessageContent() { return messageContent; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
} 