package usermanagement;

import hotelbooking.model.Booking;
import hotelbooking.model.Hotel;
import hotelbooking.model.Room;
import eventbooking.model.Event;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

public class UserDashboard {
    private String username;
    private List<Map<String, String>> hotelBookings;
    private List<Map<String, String>> eventBookings;

    public void processData(User user, List<Booking> hotelBookings, List<Event> eventBookings) {
        this.username = user != null ? user.getUsername() : "Unknown User";
        processHotelBookings(hotelBookings);
        processEventBookings(eventBookings);
    }

    private void processHotelBookings(List<Booking> bookings) {
        hotelBookings = new ArrayList<>();
        if (bookings == null || bookings.isEmpty()) return;

        for (Booking booking : bookings) {
            if (booking == null || booking.getRoom() == null) continue;

            Room room = booking.getRoom();
            Hotel hotel = room.getHotel();

            Map<String, String> bookingInfo = new HashMap<>();
            bookingInfo.put("hotelName", hotel != null ? hotel.getName() : "Unknown");
            bookingInfo.put("roomType", room.getRoomType());
            bookingInfo.put("duration", String.valueOf(
                ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate())
            ) + " days");

            hotelBookings.add(bookingInfo);
        }
    }

    private void processEventBookings(List<Event> events) {
        eventBookings = new ArrayList<>();
        if (events == null || events.isEmpty()) return;

        for (Event event : events) {
            Map<String, String> eventInfo = new HashMap<>();
            eventInfo.put("eventName", event.getName());
            eventInfo.put("location", event.getLocation());
            eventInfo.put("date", event.getDate());

            eventBookings.add(eventInfo);
        }
    }

    // Getters
    public String getUsername() { return username; }
    public List<Map<String, String>> getHotelBookings() { return hotelBookings; }
    public List<Map<String, String>> getEventBookings() { return eventBookings; }

    @Override
    public String toString() {
        return "UserDashboard{" +
               "username='" + username + '\'' +
               ", hotelBookings=" + hotelBookings +
               ", eventBookings=" + eventBookings +
               '}';
    }
}
