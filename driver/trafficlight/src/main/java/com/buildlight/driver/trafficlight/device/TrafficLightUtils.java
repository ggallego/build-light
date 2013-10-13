package com.buildlight.driver.trafficlight.device;

import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buildlight.driver.trafficlight.api.Device;
import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLightException;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

/**
 * @author ggallego
 */
public class TrafficLightUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficLightUtils.class);

    public static HIDDevice openDevice(HIDManager hidManager, Device device) {
        try {
        	return hidManager.openById(device.getVendorId(), device.getProductId(), null);
        } catch (NullPointerException | IOException e) {
            throw new TrafficLightException("Traffic light '"+device.name()+"' not found. HID message is ["+e.getMessage()+"]", e);
        }
    }

    public static void switchLedOn(HIDDevice hidDevice, Led led, byte[] writeBuffer) {
        try {
            int writtenBytes = hidDevice.write(writeBuffer);
            Validate.isTrue(writtenBytes == writeBuffer.length, "Not all bytes from the was written to usb");
            LOGGER.debug("Switch on {} Led.", led.name());
        } catch (IOException e) {
            throw new TrafficLightException("Led {"+led.name()+"} could not be switched on", e);
        }
    }

    public static void switchLedOff(HIDDevice hidDevice, Led led, byte[] writeBuffer) {
        try {
            int writtenBytes = hidDevice.write(writeBuffer);
            Validate.isTrue(writtenBytes == writeBuffer.length, "Not all bytes from the was written to usb");
            LOGGER.debug("Switch off {} Led.", led.name());
        } catch (IOException e) {
            throw new TrafficLightException("Led {"+led.name()+"} could not be switched off", e);
        }
    }

    public static void closeDevice(HIDManager hidManager, HIDDevice hidDevice) {
        try {
            hidDevice.close();
            hidManager.release();
        } catch (IOException e) {
            throw new TrafficLightException("USB Device could not be closed", e);
        }
    }
    
    public static void dumpDeviceInformation(HIDDevice hidDevice) throws IOException {
        LOGGER.debug("Manufacturer:\t{}", hidDevice.getManufacturerString());
        LOGGER.debug("Product:\t\t{}", hidDevice.getProductString());
        LOGGER.debug("SerialNumber:\t{}", hidDevice.getSerialNumberString());
    }

}
