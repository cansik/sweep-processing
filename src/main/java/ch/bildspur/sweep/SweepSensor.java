package ch.bildspur.sweep;

import io.scanse.sweep.SweepDevice;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sweep Sensor
 */
public class SweepSensor implements PConstants {

    private PApplet parent;
    private SweepDevice device;
    private Thread sweepThread;

    volatile private boolean isRunning = false;

    volatile private List<SensorSample> samples = new ArrayList<>();


    /**
     * Create a new Sweep sensor.
     * @param parent Parent processing sketch.
     */
    public SweepSensor(PApplet parent)
    {
        this.parent = parent;
        parent.registerMethod("stop", this);
    }

    /**
     * Start the sensor and listen on a specific port.
     * @param port COM Port to listen on.
     */
    public void start(String port)
    {
        if(isRunning)
            return;

        device = new SweepDevice(port);

        PApplet.println("Sweep: waiting for motors...");
        while (!device.isMotorReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PApplet.println("Sweep: running!");

        // start device
        device.startScanning();

        // create update thread
        sweepThread = new Thread(() -> {
            while(isRunning)
                updateScans();
        });

        // start update thread
        isRunning = true;
        sweepThread.start();
    }

    /**
     * Stop the sensor.
     */
    public void stop()
    {
        if(!isRunning)
            return;

        device.stopScanning();
        device.close();

        isRunning = false;

        // join thread
        try {
            sweepThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PApplet.println("Sweep: stopped!");
    }

    private void updateScans()
    {
        // turn samples to processing sample
        samples = device.nextScan().stream()
                .map(SensorSample::new).collect(Collectors.toList());
    }

    /**
     * Raw Sweep device.
     * @return Sweep device.
     */
    public SweepDevice getDevice() {
        return device;
    }

    /**
     * Check if the sensor is running.
     * @return True if the sensor is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Newest sensor samples.
     * @return Sensor samples.
     */
    public List<SensorSample> getSamples() {
        return samples;
    }
}
