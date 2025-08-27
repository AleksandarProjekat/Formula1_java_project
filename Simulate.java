package formula1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Simulate {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String driversFilePath;
        String venuesFilePath;

        // Učitavanje fajla sa vozačima
        while (true) {
            System.out.print("Enter absolute path for DRIVERS file: ");
            driversFilePath = scanner.nextLine();
            File f = new File(driversFilePath);
            if (f.exists() && f.isFile()) break;
            System.out.println("File not found. Try again.");
        }

        // Učitavanje fajla sa stazama (venues)
        while (true) {
            System.out.print("Enter absolute path for VENUES file: ");
            venuesFilePath = scanner.nextLine();
            File f = new File(venuesFilePath);
            if (f.exists() && f.isFile()) break;
            System.out.println("File not found. Try again.");
        }

        int numberOfRaces = 0;
        while (true) {
            System.out.print("How many races? (3-5 allowed): ");
            try {
                numberOfRaces = Integer.parseInt(scanner.nextLine());
                if (numberOfRaces >= 3 && numberOfRaces <= 5) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid input, try again.");
        }

        Championship championship = new Championship(driversFilePath, venuesFilePath, numberOfRaces);
        int[] selectedVenues = new int[numberOfRaces];

        System.out.println("\n--- Championship starting! ---\n");

        for (int i = 0; i < numberOfRaces; i++) {
            System.out.println("Select venue for race #" + (i + 1) + ":");
            for (int j = 0; j < championship.getVenues().size(); j++) {
                if (!championship.isVenueChosen(j)) {
                    System.out.println((j + 1) + ") " + championship.getVenues().get(j).getName());
                }
            }

            int venueChoice = -1;
            while (true) {
                try {
                    venueChoice = Integer.parseInt(scanner.nextLine());
                    if (venueChoice >= 1 && venueChoice <= championship.getVenues().size() && !championship.isVenueChosen(venueChoice - 1)) {
                        break;
                    }
                } catch (NumberFormatException ignored) {}
                System.out.println("Invalid selection, try again.");
            }

            selectedVenues[i] = venueChoice - 1;
            championship.markVenueAsChosen(selectedVenues[i]);

            System.out.println("\nPreparing race #" + (i + 1) + " at " + championship.getVenues().get(selectedVenues[i]).getName() + "...\n");

            championship.prepareDriversForRace();

            int laps = championship.getLapsForVenue(selectedVenues[i]);
            for (int lap = 0; lap < laps; lap++) {
                System.out.println("Starting lap " + (lap + 1) + "...");
                if (lap == 1) {
                    championship.decideWetTires();
                }

                championship.simulateAverageLap(selectedVenues[i]);
                championship.applyDriverSkills();
                championship.checkRainEffect(selectedVenues[i]);
                championship.evaluateMechanicalIssues();

                System.out.println("Lap " + (lap + 1) + " complete. Press ENTER to see standings.");
                scanner.nextLine();

                championship.sortDriversByTime();
                championship.showLapLeader(lap);

                System.out.println("Press ENTER to continue.");
                scanner.nextLine();
            }

            championship.updatePoints();
            championship.showRaceWinners(championship.getVenues().get(selectedVenues[i]).getName());
            System.out.println();
        }

        System.out.println("=== Championship finished! ===");
        championship.sortDriversByPoints();
        championship.announceChampion();

        scanner.close();
    }
}
