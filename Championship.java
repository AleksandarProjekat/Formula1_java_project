package formula1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Championship {

    private ArrayList<Driver> driverList;
    private ArrayList<Venue> venueList;
    private int totalRaces;
    private int currentLap;

    private final int MINOR_FAILURE = 5;
    private final int MAJOR_FAILURE = 3;
    private final int CRITICAL_FAILURE = 1;

    public Championship(String driversFile, String venuesFile, int totalRaces) throws IOException {
        this.totalRaces = totalRaces;
        this.currentLap = 0;
        driverList = new ArrayList<>();
        venueList = new ArrayList<>();

        loadDriversFromFile(driversFile);
        loadVenuesFromFile(venuesFile);
    }

    private void loadDriversFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("Invalid driver data format.");
                    System.exit(1);
                }
                driverList.add(new Driver(parts[0], Integer.parseInt(parts[1]), parts[2]));
            }
        }
    }

    private void loadVenuesFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Invalid venue data format.");
                    System.exit(1);
                }
                venueList.add(new Venue(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
            }
        }
    }

    public ArrayList<Driver> getDrivers() {
        return driverList;
    }

    public ArrayList<Venue> getVenues() {
        return venueList;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public String getVenueNameAt(int idx) {
        return venueList.get(idx).getName();
    }

    public int getLapsForVenue(int idx) {
        return venueList.get(idx).getLapsCount();
    }

    public void markVenueAsChosen(int idx) {
        venueList.get(idx).setRaceSelected(true);
    }

    public boolean isVenueChosen(int idx) {
        return venueList.get(idx).isRaceSelected();
    }

    public void prepareDriversForRace() {
        System.out.println("Race preparation started...");
        currentLap = 0;
        for (Driver d : driverList) {
            d.setEligibleToRace(true);
            d.setWetTires(false);

            int position = d.getPositionRanking();
            if (position == 1) {
                d.setTotalRaceTime(0);
            } else if (position == 2) {
                d.setTotalRaceTime(3);
            } else if (position == 3) {
                d.setTotalRaceTime(5);
            } else if (position == 4) {
                d.setTotalRaceTime(7);
            } else {
                d.setTotalRaceTime(10);
            }
        }
    }

    public void simulateAverageLap(int venueIndex) {
        int baseLap = venueList.get(venueIndex).getAvgLapDuration();
        for (Driver d : driverList) {
            if (d.isEligibleToRace()) {
                d.addToRaceTime(baseLap);
            }
        }
    }

    public void applyDriverSkills() {
        System.out.println("Applying driver special skills...");
        currentLap++;
        for (Driver d : driverList) {
            if (d.isEligibleToRace()) {
                RNG rng;
                int timeReduction = 0;
                String skill = d.getSkillSpecialization();

                if ("Overtaking".equals(skill) && currentLap % 3 == 0) {
                    rng = new RNG(10, 20);
                    timeReduction = rng.getRandomInt();
                    System.out.println("Driver " + d.getFullName() + " gained " + timeReduction + "s with overtaking.");
                } else if ("Cornering".equals(skill) || "Braking".equals(skill)) {
                    rng = new RNG(1, 8);
                    timeReduction = rng.getRandomInt();
                    System.out.println("Driver " + d.getFullName() + " gained " + timeReduction + "s with " + skill.toLowerCase() + ".");
                }
                d.setTotalRaceTime(d.getTotalRaceTime() - timeReduction);
            }
        }
    }

    public void evaluateMechanicalIssues() {
        System.out.println("Evaluating mechanical failures...");
        for (Driver d : driverList) {
            if (d.isEligibleToRace()) {
                RNG rng = new RNG(1, 100);
                int chance = rng.getRandomInt();
                if (chance == CRITICAL_FAILURE) {
                    System.out.println("Driver " + d.getFullName() + " suffered critical mechanical failure and is out.");
                    d.setEligibleToRace(false);
                    d.setTotalRaceTime(1000);
                } else if (chance <= CRITICAL_FAILURE + MAJOR_FAILURE) {
                    System.out.println("Driver " + d.getFullName() + " suffered major mechanical fault.");
                    d.addToRaceTime(120);
                } else if (chance <= CRITICAL_FAILURE + MAJOR_FAILURE + MINOR_FAILURE) {
                    System.out.println("Driver " + d.getFullName() + " suffered minor mechanical fault.");
                    d.addToRaceTime(20);
                }
            }
        }
    }

    public void showLapLeader(int lapNumber) {
        Driver leader = driverList.get(0);
        System.out.println("Lap " + (lapNumber + 1) + " leader: " + leader.getFullName());
    }

    public void showRaceWinners(String venueName) {
        System.out.println("Race at " + venueName + " winners:");
        for (int i = 0; i < Math.min(4, driverList.size()); i++) {
            System.out.println((i + 1) + ". " + driverList.get(i).getFullName());
        }
    }

    public void announceChampion() {
        System.out.println("THE CHAMPION IS " + driverList.get(0).getFullName().toUpperCase() + "!");
    }

    public void sortDriversByTime() {
        Collections.sort(driverList);
        System.out.println("Drivers sorted by race time:");
        for (Driver d : driverList) {
            System.out.println(d.getFullName() + ": " + d.getTotalRaceTime() + "s, Points: " + d.getTotalPoints());
        }
    }

    public void sortDriversByPoints() {
        // Hack: set time = points, then sort
        for (Driver d : driverList) {
            d.setTotalRaceTime(d.getTotalPoints());
        }
        Collections.sort(driverList);
        Collections.reverse(driverList);
        System.out.println("Drivers sorted by points:");
        for (Driver d : driverList) {
            System.out.println(d.getFullName() + ": " + d.getTotalPoints());
        }
    }

    public void updatePoints() {
        for (int i = 0; i < driverList.size(); i++) {
            Driver d = driverList.get(i);
            if (i < 4) {
                d.setPositionRanking(i + 1);
                int points;
                if (i + 1 == 1) {
                    points = 8;
                } else if (i + 1 == 2) {
                    points = 5;
                } else if (i + 1 == 3) {
                    points = 3;
                } else {
                    points = 1;
                }
                d.setTotalPoints(d.getTotalPoints() + points);
            } else {
                d.setPositionRanking(5);
            }
        }
    }

    public void decideWetTires() {
        RNG rng = new RNG(1, 100);
        for (Driver d : driverList) {
            if (rng.getRandomInt() < 51) {
                d.setWetTires(true);
                d.addToRaceTime(10);
                System.out.println("Driver " + d.getFullName() + " decided to use wet tires.");
            }
        }
    }

    public void checkRainEffect(int venueIndex) {
        Venue v = venueList.get(venueIndex);
        double chancePercent = v.getRainProbability() * 100;
        RNG rng = new RNG(1, 100);
        if (rng.getRandomInt() <= chancePercent) {
            System.out.println("Rain started at " + v.getName());
            for (Driver d : driverList) {
                if (!d.isUsingWetTires()) {
                    d.addToRaceTime(5);
                    System.out.println("Driver " + d.getFullName() + " did not use wet tires and lost 5 seconds.");
                }
            }
        }
    }
}
