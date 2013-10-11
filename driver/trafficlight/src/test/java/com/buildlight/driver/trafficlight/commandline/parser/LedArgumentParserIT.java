package com.buildlight.driver.trafficlight.commandline.parser;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.buildlight.driver.trafficlight.commandline.parser.ArgumentBuffer;
import com.buildlight.driver.trafficlight.commandline.parser.ArgumentParser;

/**
 * @author zutherb
 */
@ContextConfiguration(locations = "classpath:/com/buildlight/driver/trafficlight/spring-context.xml")
public class LedArgumentParserIT extends AbstractJUnit4SpringContextTests {

    @Autowired
    @Qualifier("ledArgumentParser")
    private ArgumentParser parser;

    @Test
    public void testExecuteSwitchOn() throws Exception {
        ArgumentBuffer buffer = new ArgumentBuffer(new String[]{"red", "on"});
        buffer.next();
        parser.execute(buffer);
    }

    @Test
    public void testExecuteSwitchOff() throws Exception {
        ArgumentBuffer buffer = new ArgumentBuffer(new String[]{"red", "off"});
        buffer.next();
        parser.execute(buffer);
    }
}
