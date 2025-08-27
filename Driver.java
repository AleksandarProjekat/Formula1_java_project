package formula1;

public class Driver implements Comparable<Driver> {

    private String fullName;
    private int positionRanking;
    private String skillSpecialization;
    private boolean canRace;
    private int totalRaceTime;
    private int totalPoints;
    private boolean hasWetTires;

    public Driver(String fullName, int positionRanking, String skillSpecialization) {
        this.fullName = fullName;
        this.positionRanking = positionRanking;
        this.skillSpecialization = validateSkill(skillSpecialization);
        this.canRace = true;
        this.totalRaceTime = 0;
        this.totalPoints = 0;
        this.hasWetTires = false;
    }

    private String validateSkill(String skill) {
        String skillNorm = skill.trim().toLowerCase();
        if (skillNorm.equals("braking") || skillNorm.equals("cornering") || skillNorm.equals("overtaking")) {
            return capitalizeFirst(skillNorm);
        } else {
            System.out.println("Unknown skill '" + skill + "' assigned to driver " + fullName + ". Defaulting to Cornering.");
            return "Cornering";
        }
    }

    private String capitalizeFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String getFullName() {
        return fullName;
    }

    public int getPositionRanking() {
        return positionRanking;
    }

    public void setPositionRanking(int pos) {
        this.positionRanking = pos;
    }

    public String getSkillSpecialization() {
        return skillSpecialization;
    }

    public boolean isEligibleToRace() {
        return canRace;
    }

    public void setEligibleToRace(boolean eligible) {
        this.canRace = eligible;
    }

    public int getTotalRaceTime() {
        return totalRaceTime;
    }

    public void setTotalRaceTime(int time) {
        this.totalRaceTime = time;
    }

    public void addToRaceTime(int time) {
        this.totalRaceTime += time;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int pts) {
        this.totalPoints = pts;
    }

    public void addPoints(int pts) {
        this.totalPoints += pts;
    }

    public boolean isUsingWetTires() {
        return hasWetTires;
    }

    public void setWetTires(boolean wetTires) {
        this.hasWetTires = wetTires;
    }

    @Override
    public int compareTo(Driver other) {
        if (other == null) return 1;
        return Integer.compare(this.totalRaceTime, other.totalRaceTime);
    }
}
