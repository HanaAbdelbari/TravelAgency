package eventbooking.repository;

import eventbooking.model.Event;
import eventbooking.model.EventBooking;
import eventbooking.model.EventBookingRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventRepository {
    private List<Event> events = new ArrayList<>();
    private List<EventBooking> bookings = new ArrayList<>();

    public List<Event> getAllEvents() {
        return events;
    }

    public boolean bookEvent(EventBookingRequest bookingRequest) {
        Optional<Event> eventOptional = events.stream()
                .filter(event -> event.getId().equals(bookingRequest.getEventId()))
                .findFirst();

        if (eventOptional.isPresent()) {
            EventBooking booking = new EventBooking(UUID.randomUUID().toString(), 
                bookingRequest.getUserId(), eventOptional.get());
            bookings.add(booking);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(String userId, String eventId) {
        Optional<EventBooking> booking = bookings.stream()
                .filter(b -> b.getUserId().equals(userId) && 
                           b.getEvent().getId().equals(eventId) &&
                           !b.getStatus().equals("CANCELLED"))
                .findFirst();

        if (booking.isPresent()) {
            booking.get().setStatus("CANCELLED");
            return true;
        }
        return false;
    }

    public List<Event> getUserBookings(String userId) {
        return bookings.stream()
                .filter(booking -> booking.getUserId().equals(userId) && 
                                 !booking.getStatus().equals("CANCELLED"))
                .map(EventBooking::getEvent)
                .collect(Collectors.toList());
    }
}
