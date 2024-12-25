package hotelbooking.controller;

import hotelbooking.model.Hotel;
import hotelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Get all hotels (fetch from external mock API and store in internal DB)
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        try {
            List<Hotel> hotels = hotelService.getAllHotels();
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any potential errors
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get hotel by ID (fetch from internal DB)
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        try {
            Hotel hotel = hotelService.getHotelById(id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (Exception e) {
            // Return 404 if the hotel is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a new hotel (manually for admin use)
    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        try {
            Hotel addedHotel = hotelService.addHotel(hotel);
            return new ResponseEntity<>(addedHotel, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle errors during hotel creation
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a hotel by ID
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel updatedHotel) {
        try {
            Hotel updated = hotelService.updateHotel(id, updatedHotel);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            // Return 404 if the hotel is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a hotel by ID (manually for admin use)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        try {
            hotelService.deleteHotel(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Success response without content
        } catch (Exception e) {
            // Handle errors (if the hotel doesn't exist)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
