package com.buildlight.driver.trafficlight.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zutherb
 */
public class TrafficLightMock implements TrafficLight {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficLightMock.class);

    public TrafficLightMock() {
        LOGGER.info("Traffic Light Mock was created");
    }

    @Override
    public Device getDevice() {
        return Device.MOCK;
    }

    @Override
    public void switchOn(Led led) {
        LOGGER.info("Switch on Led {}", led.name());
    }

    @Override
    public void switchOff(Led led) {
        LOGGER.info("Switch off Led {}", led.name());
    }

    @Override
    public void switchOnAllLeds() {
        LOGGER.info("Switch on all Led");
    }

    @Override
    public void switchOffAllLeds() {
        LOGGER.info("Switch off all Led");
    }

    @Override
    public void close() {
        LOGGER.info("Close traffic light");
    }
}
