package notifications.model;

public class NotificationTemplate {
    public static String getTemplate(String type) {
        switch (type) {
            case "REGISTER":
                return "Welcome, {username}! Thanks for registering with us.";
            case "RESET_PASSWORD":
                return "Hello, {username}. Click here to reset your password: {resetLink}";
            case "HOTEL_BOOK":
                return "Hi {username}, your booking at {hotelName} from {checkIn} to {checkOut} is confirmed!";
            case "HOTEL_CANCEL":
                return "Hi {username}, your booking at {hotelName} has been canceled.";
            case "NEARBY_EVENTS":
                return "Hi {username}, check out these events near your stay at {hotelName} during {dates}.";
            case "EVENT_BOOK":
                return "Hi {username}, your ticket for {eventName} on {eventDate} is confirmed!";
            case "EVENT_CANCEL":
                return "Hi {username}, your booking for {eventName} has been canceled.";
            default:
                return "Notification";
        }
    }
}
