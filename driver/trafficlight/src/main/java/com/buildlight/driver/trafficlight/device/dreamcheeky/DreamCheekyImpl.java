package com.buildlight.driver.trafficlight.device.dreamcheeky;

import static com.buildlight.driver.trafficlight.device.TrafficLightUtils.closeDevice;
import static com.buildlight.driver.trafficlight.device.TrafficLightUtils.openDevice;
import static com.buildlight.driver.trafficlight.device.TrafficLightUtils.switchLedOff;
import static com.buildlight.driver.trafficlight.device.TrafficLightUtils.switchLedOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buildlight.driver.trafficlight.api.Device;
import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

/**
 * @author ggallego
 */
public class DreamCheekyImpl implements TrafficLight {

    private static final Logger LOGGER = LoggerFactory.getLogger(DreamCheekyImpl.class);

    private final Device device = Device.DREAMCHEEKY;
    private final HIDManager hidManager;
    private final HIDDevice hidDevice;

    public DreamCheekyImpl(HIDManager hidManager) {
        this.hidManager = hidManager;
        this.hidDevice = openDevice(hidManager, getDevice());
		LOGGER.info("Traffic light 'DREAMCHEEKY' found.");
    }

    @Override
    public Device getDevice() {
    	return device;
    }
    
    @Override
    public void switchOn(Led led) {
    	byte[] buffer = new byte[]{led.getAddress(device), (byte) 0x00, (byte) 0x00, (byte) 0x00};
    	switchLedOn(hidDevice, led, buffer);
    }

    @Override
    public void switchOff(Led led) {
        byte[] buffer = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    	switchLedOff(hidDevice, led, buffer);
    }

    @Override
    public void switchOnAllLeds() {
        LOGGER.debug("This device has just one rgb led, switching on a random colour.");
        switchOn(Led.values()[(int)(Math.random()*Led.values().length)]);
    }

    @Override
    public void switchOffAllLeds() {
        LOGGER.debug("This device has just one rgb led, switching it off.");
        switchOff(Led.BLUE);
    }

    @Override
    public void close() {
    	closeDevice(hidManager, hidDevice);
    }

}
