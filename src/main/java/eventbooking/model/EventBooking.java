package eventbooking.model;

public class EventBooking {
    private String bookingId;
    private String userId;
    private Event event;
    private String status;  // CONFIRMED, CANCELLED

    public EventBooking(String bookingId, String userId, Event event) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.event = event;
        this.status = "CONFIRMED";  // Default status
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public Event getEvent() { return event; }
    public String getStatus() { return status; }

    // Setters
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setEvent(Event event) { this.event = event; }
    public void setStatus(String status) { this.status = status; }
}
