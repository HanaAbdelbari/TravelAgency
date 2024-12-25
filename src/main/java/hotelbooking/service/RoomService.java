package hotelbooking.service;

import hotelbooking.model.Room;
import hotelbooking.model.Hotel;
import hotelbooking.repository.RoomRepository;
import hotelbooking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String externalApiUrl = "https://4f6d45b1-e259-404d-a109-5e92726e0fb0.mock.pstmn.io/rooms";

    /// Fetch all rooms from external API and store them in the database if not present
    @Transactional
    public List<Room> getAllRooms() {
        // Fetch rooms from the database first
        List<Room> roomsInDb = roomRepository.findAll();

        for ( Room r : roomsInDb){
            System.out.println(r.getType());}

        // If no rooms are present in the database, fetch from external API
        if (roomsInDb.isEmpty() ) {

            System.out.println("-----------------------");
            // Fetch rooms from the external API
            Room[] roomsFromApi = restTemplate.getForObject(externalApiUrl, Room[].class);





            if (roomsFromApi != null) {
                // Iterate over the rooms and set the appropriate fields before saving
                for (Room room : roomsFromApi) {
                    // Ensure valid room type and availability
                    if (room.getType() == null || room.getType().isEmpty()) {
                        room.setType("Unknown");  // Default value if missing
                    }


                    //Set the hotel association if needed (assuming you have a way to link rooms to hotels)
                    //Optional<Hotel> hotel = hotelRepository.findById(room.getHotel());
                    //hotel.ifPresent(room::setHotel);
                }

                // Save rooms fetched from API into the database
                roomRepository.saveAll(Arrays.asList(roomsFromApi));
            }

            return Arrays.asList(roomsFromApi); // Return rooms from API
        }

        return roomsInDb; // Return rooms from the database
    }

    // Add a new room with validation for roomType
    public Room addRoom(Room room) {
        // Validate that roomType is not null or empty
        if (room.getType() == null || room.getType().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }

        // If the roomType is valid, save the room
        return roomRepository.save(room);
    }

    // Search rooms by type (Single, Double, Family) and hotel
    public List<Room> searchRooms(String roomType, Long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        return hotel.map(h -> roomRepository.findByRoomTypeAndHotel(roomType, h)).orElse(null);
    }

    // Get room details by room ID
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null); // Returns null if room not found
    }

    // Check if the room is available for booking
    public boolean checkRoomAvailability(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.isPresent() && room.get().isAvailable(); // Check the 'available' field
    }

    // Mark the room as booked
    public Room markRoomAsBooked(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent() && room.get().isAvailable()) {
            Room bookedRoom = room.get();
            bookedRoom.setAvailable(false);  // Set room as unavailable
            roomRepository.save(bookedRoom); // Save changes
            return bookedRoom;
        }
        return null; // Return null if room not found or already booked
    }

    // Mark the room as available (on cancellation)
    public Room markRoomAsAvailable(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setAvailable(true);  // Set room as available
            roomRepository.save(room); // Save changes
            return room;  // Return the updated room
        }
        return null;  // Return null if room not found
    }
}
