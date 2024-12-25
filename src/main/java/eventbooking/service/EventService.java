package eventbooking.service;

import eventbooking.model.Event;
import eventbooking.model.EventBookingRequest;
import eventbooking.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

public class EventService {

    private EventRepository eventRepository = new EventRepository();

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public List<Event> searchEvents(String query) {
        if (query == null || query.isEmpty()) {
            return eventRepository.getAllEvents();
        }
        return eventRepository.getAllEvents().stream()
                .filter(event -> event.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean bookEvent(EventBookingRequest bookingRequest) {
        return eventRepository.bookEvent(bookingRequest);
    }

    public boolean cancelBooking(String userId, String eventId) {
        return eventRepository.cancelBooking(userId, eventId);
    }

    public List<Event> getRecommendedEvents(String location, String startDate, String endDate) {
        return eventRepository.getAllEvents().stream()
                .filter(event -> event.getLocation().equalsIgnoreCase(location) &&
                        event.getDate().compareTo(startDate) >= 0 &&
                        event.getDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    public List<Event> getUserBookings(String userId) {
        return eventRepository.getUserBookings(userId);
    }
}
