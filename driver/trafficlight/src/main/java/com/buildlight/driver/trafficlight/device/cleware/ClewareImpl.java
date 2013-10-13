package com.buildlight.driver.trafficlight.device.cleware;

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
 * @author zutherb
 */
public class ClewareImpl implements TrafficLight {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClewareImpl.class);

    private final Device device = Device.CLEWARE;
    private final HIDManager hidManager;
    private final HIDDevice hidDevice;

    public ClewareImpl(HIDManager hidManager) {
        this.hidManager = hidManager;
        this.hidDevice = openDevice(hidManager, getDevice());
		LOGGER.info("Traffic light 'CLEWARE' found.");
    }

    @Override
    public Device getDevice() {
    	return device;
    }
    
    @Override
    public void switchOn(Led led) {
        byte[] buffer = new byte[]{(byte) 0x0, (byte) 0x0, led.getAddress(device), (byte) 0x1};
    	switchLedOn(hidDevice, led, buffer);
    }

    @Override
    public void switchOff(Led led) {
        byte[] buffer = new byte[]{(byte) 0x0, (byte) 0x0, led.getAddress(device), (byte) 0x0};
    	switchLedOff(hidDevice, led, buffer);
    }

    @Override
    public void switchOnAllLeds() {
        for (Led led : Led.values()) {
            switchOn(led);
        }
    }

    @Override
    public void switchOffAllLeds() {
        for (Led led : Led.values()) {
            switchOff(led);
        }
    }

    @Override
    public void close() {
    	closeDevice(hidManager, hidDevice);
    }
    
}