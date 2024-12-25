package hotelbooking.repository;

import hotelbooking.model.Hotel;
import hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Custom query to find rooms by type and hotel
    List<Room> findByRoomTypeAndHotel(String roomType, Hotel hotel);

    // Other query methods you may have:

    //List<Room> findByHotelId(Long hotelId);
    //List<Room> findByAvailableTrue();

}
