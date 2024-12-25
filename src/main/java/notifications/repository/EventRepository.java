package notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import eventbooking.model.Event;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.venue = ?1 AND e.date BETWEEN ?2 AND ?3")
    List<Event> findByVenueAndDateBetween(
        String venue,
        LocalDateTime checkIn, 
        LocalDateTime checkOut
    );
} 