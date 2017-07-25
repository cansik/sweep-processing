package ch.bildspur.sweep;

import io.scanse.sweep.SweepSample;
import processing.core.PApplet;
import processing.core.PVector;

public class SensorSample {
    private SweepSample sample;

    private float angle;
    private int signalStrength;
    private int distance;
    private PVector location;

    public SensorSample(SweepSample sample)
    {
        this.sample = sample;

        this.angle = sample.getAngle() / -1000f;
        this.distance = sample.getDistance();
        this.signalStrength = sample.getSignalStrength();

        float x = distance * PApplet.cos(PApplet.radians(this.angle));
        float y = distance * PApplet.sin(PApplet.radians(this.angle));

        location = new PVector(x, y);
    }

    public SweepSample getSweepSample() {
        return sample;
    }

    public float getAngle() {
        return angle;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public int getDistance() {
        return distance;
    }

    public PVector getLocation() {
        return location;
    }
}
