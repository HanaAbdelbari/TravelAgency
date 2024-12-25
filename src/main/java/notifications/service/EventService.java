package notifications.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import eventbooking.model.Event;
import notifications.repository.EventRepository;
import hotelbooking.model.Hotel;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    public List<Event> findNearbyEvents(Hotel hotel, LocalDateTime checkIn, LocalDateTime checkOut) {
        // Find events in the same hotel or venue during the stay
        return eventRepository.findByVenueAndDateBetween(
            hotel.getName(),  // Using hotel name as the venue
            checkIn,
            checkOut
        );
    }
} 