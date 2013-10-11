package com.buildlight.driver.trafficlight.device.cleware;

import static java.lang.String.format;

import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buildlight.driver.trafficlight.api.Device;
import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.buildlight.driver.trafficlight.api.TrafficLightException;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

/**
 * @author zutherb
 */
public class ClewareImpl implements TrafficLight {

    private static final int VENDOR_ID = 0xD50;     //Cleware Vendor Id
    private static final int PRODUCT_ID = 0x8;      //Traffic Light Product Id

    private static final Logger LOGGER = LoggerFactory.getLogger(ClewareImpl.class);

    private static final String SWITCH_ON_ERROR = "Led {} could not be switched on";
    private static final String SWITCH_OFF_ERROR = "Led {} could not be switched off";

    private final HIDManager hidManager;
    private final HIDDevice hidDevice;

    public ClewareImpl(HIDManager hidManager) throws IOException {
        this.hidManager = hidManager;
        this.hidDevice = hidManager.openById(VENDOR_ID, PRODUCT_ID, null);
    }

    @Override
    public Device getDevice() {
    	return Device.CLEWARE;
    }
    
    @Override
    public void switchOn(Led led) {
        try {
            byte[] writeBuffer = createSwitchOnBuffer(led);
            int writtenBytes = hidDevice.write(writeBuffer);
            Validate.isTrue(writtenBytes == writeBuffer.length, "Not all bytes from the was written to usb");
            LOGGER.debug(format("Switch on '%s' Led", led.name()));
        } catch (IOException e) {
            LOGGER.error(SWITCH_ON_ERROR, led.name());
            throw new TrafficLightException(e);
        }
    }

    @Override
    public void switchOff(Led led) {
        try {
            byte[] writeBuffer = createSwitchOffBuffer(led);
            int writtenBytes = hidDevice.write(writeBuffer);
            Validate.isTrue(writtenBytes == writeBuffer.length, "Not all bytes from the was written to usb");
            LOGGER.debug(format("Switch off '%s' Led", led.name()));
        } catch (IOException e) {
            LOGGER.error(SWITCH_OFF_ERROR, led.name());
            throw new TrafficLightException(e);
        }
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

    private byte[] createSwitchOnBuffer(Led led) {
        return new byte[]{(byte) 0x0, (byte) 0x0, led.getClewareAddress(), (byte) 0x1};
    }

    private byte[] createSwitchOffBuffer(Led led) {
        return new byte[]{(byte) 0x0, (byte) 0x0, led.getClewareAddress(), (byte) 0x0};
    }

    @Override
    public void close() {
        try {
            hidDevice.close();
            hidManager.release();
        } catch (IOException e) {
            LOGGER.error("USB Device could not be closed");
            throw new TrafficLightException(e);
        }
    }
    
    public void dumpDebugInformation() throws IOException {
        LOGGER.debug("Manufacturer:\t{}", hidDevice.getManufacturerString());
        LOGGER.debug("Product:\t\t{}", hidDevice.getProductString());
        LOGGER.debug("SerialNumber:\t{}", hidDevice.getSerialNumberString());
    }

}
