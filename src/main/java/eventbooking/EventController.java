package eventbooking;

import eventbooking.model.Event;
import eventbooking.model.EventBookingRequest;
import eventbooking.service.EventService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventController {

    private EventService eventService = new EventService();

    @GET
    public Response getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return Response.ok(events).build();
    }

    @GET
    @Path("/search")
    public Response searchEvents(@QueryParam("query") String query) {
        List<Event> events = eventService.searchEvents(query);
        return Response.ok(events).build();
    }

    @POST
    @Path("/bookings")
    public Response bookEvent(EventBookingRequest bookingRequest) {
        boolean isBooked = eventService.bookEvent(bookingRequest);
        if (isBooked) {
            return Response.ok("Event booked successfully!").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to book the event").build();
    }

    @GET
    @Path("/recommendations")
    public Response getRecommendations(@QueryParam("location") String location,
                                       @QueryParam("startDate") String startDate,
                                       @QueryParam("endDate") String endDate) {
        List<Event> recommendations = eventService.getRecommendedEvents(location, startDate, endDate);
        return Response.ok(recommendations).build();
    }
}
