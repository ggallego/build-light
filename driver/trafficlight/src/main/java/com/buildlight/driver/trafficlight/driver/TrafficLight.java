package com.buildlight.driver.trafficlight.driver;

/**
 * @author zutherb
 */
public interface TrafficLight {
    void switchOn(Led led);

    void switchOff(Led led);

    void switchOnAllLeds();

    void switchOffAllLeds();

    void close();
}
