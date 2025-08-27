package formula1;

public class Venue {

    private String name;
    private int lapsCount;
    private int avgLapDuration;
    private double rainProbability;
    private boolean raceSelected;

    public Venue(String name, int lapsCount, int avgLapDuration, double rainProbability) {
        this.name = name;
        this.lapsCount = lapsCount;
        this.avgLapDuration = avgLapDuration;
        this.rainProbability = rainProbability;
        this.raceSelected = false;
    }

    public String getName() {
        return name;
    }

    public int getLapsCount() {
        return lapsCount;
    }

    public int getAvgLapDuration() {
        return avgLapDuration;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public boolean isRaceSelected() {
        return raceSelected;
    }

    public void setRaceSelected(boolean selected) {
        this.raceSelected = selected;
        System.out.println("Venue '" + name + "' has been selected for a race.");
    }
}
