package com.buildlight.driver.trafficlight.api;

/**
 * @author zutherb
 */
public interface TrafficLight {
	
	Device getDevice();
	
    void switchOn(Led led);

    void switchOff(Led led);

    void switchOnAllLeds();

    void switchOffAllLeds();

    void close();
}
