import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * @version 8.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}

/**
 * =========================================================
 * CLASS - Reservation
 * =========================================================
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * =========================================================
 * CLASS - BookingHistory
 * =========================================================
 */
class BookingHistory {

    // List to store confirmed bookings
    private List<Reservation> reservations = new ArrayList<>();

    // Add reservation to history
    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }
}

/**
 * =========================================================
 * CLASS - BookingReportService
 * =========================================================
 */
class BookingReportService {

    // Generate booking report
    public void generateReport(BookingHistory history) {

        System.out.println("Booking History Report\n");

        for (Reservation r : history.getReservations()) {
            System.out.println("Guest: "
                    + r.getGuestName()
                    + ", Room Type: "
                    + r.getRoomType());
        }
    }
}