package usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hotelbooking.service.BookingService;
import eventbooking.service.EventService;
import hotelbooking.model.Booking;
import eventbooking.model.Event;

import java.util.List;

@Service
public class UserDashboardService {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    public UserDashboard getUserDashboard(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        UserDashboard dashboard = new UserDashboard();

        // Fetch user profile
        User user = userService.getUserProfile(userId);

        // Fetch bookings
        List<Booking> hotelBookings = bookingService.getUserBookings(userId);
        List<Event> eventBookings = eventService.getUserBookings(userId.toString());

        // Process and populate dashboard
        dashboard.processData(user, hotelBookings, eventBookings);

        return dashboard;
    }
}
