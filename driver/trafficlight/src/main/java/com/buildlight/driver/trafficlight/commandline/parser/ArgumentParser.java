package com.buildlight.driver.trafficlight.commandline.parser;

import com.buildlight.driver.trafficlight.driver.TrafficLight;

/**
 * @author zutherb
 */
public interface ArgumentParser {
    boolean isResponsible(ArgumentBuffer buffer);

    void execute(ArgumentBuffer buffer);

    TrafficLight trafficLight();
}
