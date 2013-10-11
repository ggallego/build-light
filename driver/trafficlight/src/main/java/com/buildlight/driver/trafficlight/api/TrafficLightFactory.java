package com.buildlight.driver.trafficlight.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.buildlight.driver.trafficlight.device.dreamcheeky.DreamCheekyImpl;
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
            return new DreamCheekyImpl(hidManager);
            //return new ClewareImpl(hidManager);
        } catch (IOException e) {
            throw new TrafficLightException("Traffic light USB device could not be found.", e);
        }
    }

}
