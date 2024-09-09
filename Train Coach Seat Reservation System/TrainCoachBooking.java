import java.util.Scanner;

public class TrainCoachBooking {
    private static final int TOTAL_ROWS = 11; // 10 rows with 7 seats and 1 row with 3 seats
    private static final int SEATS_PER_ROW = 7;
    private static final int LAST_ROW_SEATS = 3;
    private static final int MAX_BOOKING = 7;

    private static boolean[][] seats = new boolean[TOTAL_ROWS][SEATS_PER_ROW];
    private static boolean[] lastRowSeats = new boolean[LAST_ROW_SEATS];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the number of seats to book (0 to exit): ");
            int numSeats = scanner.nextInt();

            if (numSeats == 0) {
                break;
            }

            if (numSeats > MAX_BOOKING) {
                System.out.println("You can book up to " + MAX_BOOKING + " seats at a time.");
                continue;
            }

            bookSeats(numSeats);
            displaySeats();
        }
    }

    private static void bookSeats(int numSeats) {
        int booked = 0;

        // Try to book seats in one row
        for (int i = 0; i < TOTAL_ROWS - 1; i++) {
            int available = getAvailableSeatsInRow(i);
            if (available >= numSeats) {
                bookSeatsInRow(i, numSeats);
                booked = numSeats;
                break;
            }
        }

        // If not enough seats available in one row, book nearby seats
        if (booked < numSeats) {
            int remaining = numSeats - booked;
            bookNearbySeats(remaining);
        }
    }

    private static int getAvailableSeatsInRow(int row) {
        int available = 0;
        for (int i = 0; i < SEATS_PER_ROW; i++) {
            if (!seats[row][i]) {
                available++;
            }
        }
        return available;
    }

    private static void bookSeatsInRow(int row, int numSeats) {
        for (int i = 0; i < SEATS_PER_ROW; i++) {
            if (!seats[row][i]) {
                seats[row][i] = true;
                numSeats--;
                if (numSeats == 0) {
                    break;
                }
            }
        }
    }

    private static void bookNearbySeats(int numSeats) {
        for (int i = 0; i < TOTAL_ROWS - 1; i++) {
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                if (!seats[i][j]) {
                    seats[i][j] = true;
                    numSeats--;
                    if (numSeats == 0) {
                        return;
                    }
                }
            }
        }

        // Book seats in the last row
        for (int i = 0; i < LAST_ROW_SEATS; i++) {
            if (!lastRowSeats[i]) {
                lastRowSeats[i] = true;
                numSeats--;
                if (numSeats == 0) {
                    return;
                }
            }
        }
    }

    private static void displaySeats() {
        System.out.println("Coach Seats:");
        for (int i = 0; i < TOTAL_ROWS - 1; i++) {
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                System.out.print(seats[i][j] ? "X " : "O ");
            }
            System.out.println();
        }

        System.out.print("Last Row: ");
        for (int i = 0; i < LAST_ROW_SEATS; i++) {
            System.out.print(lastRowSeats[i] ? "X " : "O ");
        }
        System.out.println();
    }
}