package com.buildlight.application.interrogator;

import com.buildlight.driver.trafficlight.api.Led;

/**
 * @author zutherb
 */
public enum BuildState {
    Successful(Led.GREEN),
    Building(Led.YELLOW),
    Failed(Led.RED);

    private Led led;

    BuildState(Led led) {
        this.led = led;
    }

    public Led getLed() {
        return led;
    }
}
