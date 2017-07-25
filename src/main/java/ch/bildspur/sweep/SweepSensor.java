package ch.bildspur.sweep;

import io.scanse.sweep.SweepDevice;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.List;
import java.util.stream.Collectors;

public class SweepSensor implements PConstants {

    private PApplet parent;
    private SweepDevice device;

    private boolean isRunning = false;

    private List<SensorSample> samples;

    public SweepSensor(PApplet parent)
    {
        this.parent = parent;

        // register methods
        parent.registerMethod("pre", this);
    }

    public void start(String port)
    {
        if(isRunning)
            return;

        device = new SweepDevice(port);

        PApplet.println("waiting for sweep motors...");
        while (!device.isMotorReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PApplet.println("running!");

        // start device
        device.startScanning();
        isRunning = true;
    }

    public void pre()
    {
        if(!isRunning)
            return;

        // turn samples to processing sample
        samples = device.nextScan().stream()
                .map(SensorSample::new).collect(Collectors.toList());
    }

    public void stop()
    {
        if(!isRunning)
            return;

        device.stopScanning();
        device.close();
        isRunning = false;
    }

    public SweepDevice getDevice() {
        return device;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public List<SensorSample> getSamples() {
        return samples;
    }
}
