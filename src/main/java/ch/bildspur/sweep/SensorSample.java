package ch.bildspur.sweep;

import io.scanse.sweep.SweepSample;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Sweep Sensor Sample for Processing
 */
public class SensorSample {
    private SweepSample sample;

    private float angle;
    private int signalStrength;
    private int distance;
    private PVector location;

    /**
     * Create a new sensor sample.
     * @param sample Raw Sweep Sample.
     */
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

    /**
     * Raw Sweep sample.
     * @return Raw Sweep sample.
     */
    public SweepSample getSweepSample() {
        return sample;
    }

    /**
     * Angle in degrees 0-360°.
     * @return Angle in degrees.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Signal strength as short (0-255).
     * @return Signal strength.
     */
    public int getSignalStrength() {
        return signalStrength;
    }

    /**
     * Distance.
     * @return Distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * PVector that represents the location in processing space.
     * @return PVector with the location.
     */
    public PVector getLocation() {
        return location;
    }
}
