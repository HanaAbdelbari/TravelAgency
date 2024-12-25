package hotelbooking.controller;

import hotelbooking.model.Room;
import hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms") // Base URL for room-related APIs
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Fetch all rooms (will fetch from external API if no rooms in database)
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.noContent().build(); // No rooms available
        }
        return ResponseEntity.ok(rooms); // Return the list of rooms
    }

    // Search rooms by type and hotel
    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRooms(
            @RequestParam String type,
            @RequestParam Long hotelId) {
        List<Room> rooms = roomService.searchRooms(type, hotelId);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rooms);
    }

    // Get room details by ID
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    // Check room availability by room ID
    @GetMapping("/{roomId}/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(@PathVariable Long roomId) {
        boolean isAvailable = roomService.checkRoomAvailability(roomId);
        return ResponseEntity.ok(isAvailable);
    }

    // Book a room (mark as unavailable)
    @PostMapping("/{roomId}/book")
    public ResponseEntity<Room> bookRoom(@PathVariable Long roomId) {
        Room bookedRoom = roomService.markRoomAsBooked(roomId);
        if (bookedRoom == null) {
            return ResponseEntity.badRequest().build(); // Room unavailable or not found
        }
        return ResponseEntity.ok(bookedRoom);
    }

    // Cancel booking (mark room as available)
    @PostMapping("/{roomId}/cancel")
    public ResponseEntity<Room> cancelBooking(@PathVariable Long roomId) {
        Room availableRoom = roomService.markRoomAsAvailable(roomId);
        if (availableRoom == null) {
            return ResponseEntity.badRequest().build(); // Room not found
        }
        return ResponseEntity.ok(availableRoom);
    }

    // Add a new room
    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        // Ensure roomType is not null or empty
        if (room.getType() == null || room.getType().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Bad request if roomType is missing
        }

        Room savedRoom = roomService.addRoom(room);
        return ResponseEntity.ok(savedRoom); // Return the saved room
    }
}
