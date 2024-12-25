package notifications.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {
    public String buildHotelReservationTemplate(String hotelName, String dates, String roomType) {
        return String.format("""
            Dear Guest,
            
            Your hotel reservation has been confirmed:
            
            Hotel: %s
            Dates: %s
            Room Type: %s
            
            Need to modify your reservation? Please visit our website or contact us.
            
            Thank you for choosing our service!
            """, hotelName, dates, roomType);
    }

    public String buildEventReservationTemplate(String eventName, String date, int numberOfTickets) {
        return String.format("""
            Dear Guest,
            
            Your event reservation is confirmed!
            
            Event: %s
            Date: %s
            Number of Tickets: %d
            
            Please arrive 30 minutes before the event starts.
            Don't forget to bring your ID for verification.
            
            Enjoy the event!
            """, eventName, date, numberOfTickets);
    }

    public String buildPasswordResetTemplate(String resetLink, String username) {
        return String.format("""
            Dear %s,
            
            We received a request to reset your password.
            
            Click the link below to reset your password:
            %s
            
            This link will expire in 24 hours.
            
            If you didn't request this, please ignore this email or contact support.
            
            Best regards,
            Travel Agency Support Team
            """, username, resetLink);
    }

    public String buildPromotionTemplate(String offerDetails, String validUntil, String promoCode) {
        return String.format("""
            Special Offer Alert! 
            
            %s
            
            Use Promo Code: %s
            Valid Until: %s
            
            Book now to avail this exclusive offer!
            
            Terms and conditions apply.
            """, offerDetails, promoCode, validUntil);
    }

    public String buildNearbyEventTemplate(String hotelName, String eventName, String eventDate) {
        return String.format("""
            Events at %s!
            
            During your stay, don't miss:
            
            Event: %s
            Date: %s
            
            Want to book tickets? Visit our reception desk!
            
            Enjoy your stay!
            """, hotelName, eventName, eventDate);
    }

    public String buildEventModificationTemplate(String eventName, String oldDate, String newDate, String reason) {
        return String.format("""
            Important Update About Your Event!
            
            Event: %s
            
            Change Details:
            Original Date: %s
            New Date: %s
            
            Reason for change: %s
            
            Your reservation has been automatically updated.
            If the new date doesn't work for you, please contact us within 48 hours for a full refund.
            
            We apologize for any inconvenience.
            """, eventName, oldDate, newDate, reason);
    }

    public String buildRegistrationTemplate(String username) {
        return String.format("""
            Welcome to Our Travel Agency, %s!
            
            Thank you for registering with us. Your account has been successfully created.
            
            You can now:
            - Book hotels
            - Reserve event tickets
            - Get notifications about nearby events
            - Access exclusive promotions
            
            Start exploring our services today!
            
            Best regards,
            Travel Agency Team
            """, username);
    }
} 