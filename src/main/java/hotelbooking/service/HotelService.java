package hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hotelbooking.model.Hotel;
import hotelbooking.repository.HotelRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    private RestTemplate restTemplate;

    private final String externalApiUrl = "https://4f6d45b1-e259-404d-a109-5e92726e0fb0.mock.pstmn.io/hotels";

    // Fetch all hotels, save them to the database if not already present
    @Transactional
    public List<Hotel> getAllHotels() {
        // Fetch hotels from the database
        List<Hotel> hotelsInDb = hotelRepository.findAll();

        // If there are no hotels in the database, fetch from the external API
        if (hotelsInDb.isEmpty()) {
            Hotel[] hotelsFromApi = restTemplate.getForObject(externalApiUrl, Hotel[].class);

            if (hotelsFromApi != null) {
                hotelRepository.saveAll(Arrays.asList(hotelsFromApi));
            }
            return Arrays.asList(hotelsFromApi); // Return fetched hotels
        }

        return hotelsInDb; // Return hotels from the database
    }

    // Fetch a hotel by ID
    @Transactional(readOnly = true)
    public Hotel getHotelById(Long id) {
        Optional<Hotel> hotelInDb = hotelRepository.findById(id);

        if (hotelInDb.isPresent()) {
            return hotelInDb.get();
        } else {
            String apiUrlWithId = externalApiUrl + "/" + id;
            Hotel hotelFromApi = restTemplate.getForObject(apiUrlWithId, Hotel.class);

            if (hotelFromApi != null) {
                hotelRepository.save(hotelFromApi);
            }
            return hotelFromApi;
        }
    }

    // Add a new hotel
    @Transactional
    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    // Update an existing hotel
    @Transactional
    public Hotel updateHotel(Long id, Hotel updatedHotel) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(updatedHotel.getName());
                    hotel.setLocation(updatedHotel.getLocation());

                    // Save the updated hotel
                    return hotelRepository.save(hotel); // Save updated hotel
                })
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    // Delete a hotel
    @Transactional
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
