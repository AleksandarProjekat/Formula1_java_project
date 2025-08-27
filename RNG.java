package formula1;

import java.util.Random;

public class RNG {

    private final Random randomGenerator;
    private int lowerBound;
    private int upperBound;

    public RNG() {
        this.randomGenerator = new Random();
        this.lowerBound = 0;
        this.upperBound = 100;
    }

    public RNG(int lowerBound, int upperBound) {
        this.randomGenerator = new Random();
        if (upperBound <= lowerBound) {
            System.out.println("Upper bound must be greater than lower bound. Using default bounds 0-100.");
            this.lowerBound = 0;
            this.upperBound = 100;
        } else {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
    }

    public int getRandomInt() {
        int range = upperBound - lowerBound + 1;
        return randomGenerator.nextInt(range) + lowerBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
