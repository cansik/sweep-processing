package ch.bildspur.sweep.test;


import ch.bildspur.sweep.SensorSample;
import ch.bildspur.sweep.SweepSensor;
import processing.core.PApplet;
import processing.opengl.PJOGL;

/**
 * Created by cansik on 21.03.17.
 */
public class Sketch extends PApplet {
    public final static int OUTPUT_WIDTH = 640;
    public final static int OUTPUT_HEIGHT = 480;

    public final static int FRAME_RATE = 60;

    SweepSensor sweep;

    public void settings() {
        size(OUTPUT_WIDTH, OUTPUT_HEIGHT, FX2D);
        PJOGL.profile = 1;
    }

    public void setup() {
        frameRate(FRAME_RATE);

        sweep = new SweepSensor(this);
        sweep.start("/dev/tty.usbserial-DO004HM4", 5, 500);
    }

    public void draw() {
        // clear screen
        background(0);

        pushMatrix();
        translate(width / 2, height / 2);
        for(SensorSample sample : sweep.getSamples())
        {
            pushMatrix();
            translate(sample.getLocation().x, sample.getLocation().y);
            fill(255);
            noStroke();
            ellipse(0, 0, 10, 10);
            popMatrix();
        }
        popMatrix();

        fill(0, 255, 0);
        text("FPS: " + frameRate, 20, 20);
    }
}
