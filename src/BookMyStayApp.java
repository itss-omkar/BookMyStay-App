import java.util.Scanner;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * @version 9.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Booking Validation");

        try {
            // Input
            System.out.print("Enter guest name: ");
            String name = sc.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            // Validate
            BookingValidator.validateRoomType(roomType);

            // If valid
            System.out.println("Booking successful for " + name + " (" + roomType + ")");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        sc.close();
    }
}

/**
 * =========================================================
 * CLASS - BookingValidator
 * =========================================================
 */
class BookingValidator {

    public static void validateRoomType(String roomType) throws InvalidBookingException {

        // Case-sensitive validation (as mentioned)
        if (!(roomType.equals("Single") ||
                roomType.equals("Double") ||
                roomType.equals("Suite"))) {

            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

/**
 * =========================================================
 * CLASS - InvalidBookingException
 * =========================================================
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}