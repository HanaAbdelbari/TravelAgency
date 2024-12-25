package hotelbooking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomType; // Single, Double, Family

    @Column(nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    // Constructors
    public Room() {}

    public Room(String roomType, boolean available, Hotel hotel) {
        this.roomType = roomType;
        this.available = available;
        this.hotel = hotel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return roomType;
    }

    public void setType(String type) {
        this.roomType = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
