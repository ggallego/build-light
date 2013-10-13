package com.buildlight.driver.trafficlight.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.buildlight.driver.trafficlight.device.cleware.ClewareImpl;
import com.buildlight.driver.trafficlight.device.dreamcheeky.DreamCheekyImpl;
import com.buildlight.driver.trafficlight.device.mock.TrafficLightMockImpl;
import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDManager;

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

    public synchronized TrafficLight instance() {
        if (INSTANCE == null) {
            List<String> profiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
            if (profiles.contains("test")) {
                INSTANCE = new TrafficLightMockImpl();
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
    	ClassPathLibraryLoader.loadNativeHIDLibrary();
    	for (Device device : Device.values()) {
    		try {
        		switch (device) {
        		case CLEWARE:
        			return new ClewareImpl(HIDManager.getInstance());
        		case DREAMCHEEKY:
        			return new DreamCheekyImpl(HIDManager.getInstance());
        		case MOCK:
        		default:
        			break;
        		}
        	} catch (TrafficLightException e) {
        		LOGGER.info(e.getMessage());
        	} catch (IOException e) {
                throw new TrafficLightException("HID (USB) manager could not be opened. HID message is ["+e.getMessage()+"]", e);
			}
    	}
    	return new TrafficLightMockImpl();
    }

}
