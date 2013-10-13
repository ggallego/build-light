package com.buildlight.driver.trafficlight.device.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buildlight.driver.trafficlight.api.Device;
import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;

/**
 * @author zutherb
 */
public class TrafficLightMockImpl implements TrafficLight {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficLightMockImpl.class);

    public TrafficLightMockImpl() {
		LOGGER.info("Traffic light 'MOCK' found.");
    }

    @Override
    public Device getDevice() {
        return Device.MOCK;
    }

    @Override
    public void switchOn(Led led) {
        LOGGER.info("Switch on Led {}.", led.name());
    }

    @Override
    public void switchOff(Led led) {
        LOGGER.info("Switch off Led {}.", led.name());
    }

    @Override
    public void switchOnAllLeds() {
        LOGGER.info("Switch on all Leds.");
    }

    @Override
    public void switchOffAllLeds() {
        LOGGER.info("Switch off all Leds.");
    }

    @Override
    public void close() {
        LOGGER.info("Traffic light 'MOCK' was closed.");
    }
}
