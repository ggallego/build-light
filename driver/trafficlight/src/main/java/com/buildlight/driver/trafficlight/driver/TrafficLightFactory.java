package com.buildlight.driver.trafficlight.driver;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zutherb
 */
@Component
public class TrafficLightFactory {

    private ApplicationContext applicationContext;

    @Autowired
    public TrafficLightFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static TrafficLight INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficLightFactory.class);

    private static final int VENDOR_ID = 0xD50;     //Cleware Vendor Id
    private static final int PRODUCT_ID = 0x8;      //Traffic Light Product Id

    public synchronized TrafficLight instance() {
        if (INSTANCE == null) {
            List<String> profiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
            if (profiles.contains("test")) {
                INSTANCE = new TrafficLightMock();
            } else {
                INSTANCE = createNewInstance();
            }
        }
        return INSTANCE;
    }

    public synchronized static TrafficLight getInstance() {
        if (INSTANCE == null) {
            INSTANCE = createNewInstance();
        }
        return INSTANCE;
    }

    public static TrafficLight createNewInstance() {
        try {
            ClassPathLibraryLoader.loadNativeHIDLibrary();
            HIDManager hidManager = HIDManager.getInstance();
            HIDDevice hidDevice = hidManager.openById(VENDOR_ID, PRODUCT_ID, null);
            dumpDebugInformation(hidDevice);
            return new TrafficLightImpl(hidManager, hidDevice);
        } catch (IOException e) {
            throw new TrafficLightException("Traffic light USB device could not be found.", e);
        }
    }

    private static void dumpDebugInformation(HIDDevice hidDevice) throws IOException {
        LOGGER.debug("Manufacturer:\t{}", hidDevice.getManufacturerString());
        LOGGER.debug("Product:\t\t{}", hidDevice.getProductString());
        LOGGER.debug("SerialNumber:\t{}", hidDevice.getSerialNumberString());
    }
}
