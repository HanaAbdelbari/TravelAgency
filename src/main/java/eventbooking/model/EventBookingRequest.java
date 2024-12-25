package eventbooking.model;

public class EventBookingRequest {
    private String eventId;
    private String userId;
    private int numberOfTickets;

    // Getters
    public String getEventId() { return eventId; }
    public String getUserId() { return userId; }
    public int getNumberOfTickets() { return numberOfTickets; }

    // Setters
    public void setEventId(String eventId) { this.eventId = eventId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setNumberOfTickets(int numberOfTickets) { this.numberOfTickets = numberOfTickets; }
}
