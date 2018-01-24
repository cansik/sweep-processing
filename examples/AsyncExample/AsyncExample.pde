import ch.bildspur.sweep.*;

SweepSensor sweep;

void setup()
{
  size(600, 300, FX2D);

  sweep = new SweepSensor(this);
  sweep.startAsync("/dev/tty.usbserial-DO004HM4", 5, 500);
}

void draw()
{
  background(55);

  pushMatrix();
  translate(width / 2, height / 2);

  // show sweep position
  fill(255, 255, 0);
  ellipse(0, 0, 50, 50);

  // show all sweep samples
  for (SensorSample sample : sweep.getSamples())
  {
    pushMatrix();
    translate(sample.getLocation().x, sample.getLocation().y);
    fill(255, 0, sample.getSignalStrength());
    noStroke();
    ellipse(0, 0, 10, 10);
    popMatrix();
  }
  popMatrix();

  fill(0, 255, 0);
  text("FPS: " + frameRate, 20, 20);
}