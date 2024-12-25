package eventbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Logic for searching events by location and date
    public List<Event> searchEvents(String location, Date date) {
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        return eventRepository.findByLocationAndDate(location, date);
    }

    // Logic for retrieving an event by its ID
    public Event getEventById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid event ID.");
        }

        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new RuntimeException("Event not found with ID: " + id);
        }
        return event.get();
    }

    // Logic for creating a new event
    public Event createEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be null or empty.");
        }
        if (event.getLocation() == null || event.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Event location cannot be null or empty.");
        }
        if (event.getDate() == null) {
            throw new IllegalArgumentException("Event date cannot be null.");
        }

        return eventRepository.save(event);
    }

    // Logic for deleting an event by its ID
    public void deleteEvent(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid event ID.");
        }

        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with ID: " + id);
        }

        eventRepository.deleteById(id);
    }
}
