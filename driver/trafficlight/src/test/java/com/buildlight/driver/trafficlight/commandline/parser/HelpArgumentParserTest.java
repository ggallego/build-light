package com.buildlight.driver.trafficlight.commandline.parser;

import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.buildlight.driver.trafficlight.commandline.parser.ArgumentBuffer;
import com.buildlight.driver.trafficlight.commandline.parser.ArgumentParser;
import com.buildlight.driver.trafficlight.commandline.parser.HelpArgumentParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class HelpArgumentParserTest {
    @Mock
    private TrafficLight light;

    @InjectMocks
    private ArgumentParser parser = new HelpArgumentParser(light);

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testIsResponsibleFalse() throws Exception {
        ArgumentBuffer buffer = new ArgumentBuffer(new String[]{"--help"});
        buffer.next();
        assertTrue(parser.isResponsible(buffer));
    }

    @Test
    public void testIsResponsibleTrue() throws Exception {
        ArgumentBuffer buffer = new ArgumentBuffer(new String[]{"--gui"});
        buffer.next();
        assertFalse(parser.isResponsible(buffer));
    }

    @Test
    public void testExecute() throws Exception {
        parser.execute(mock(ArgumentBuffer.class));
        verifyZeroInteractions(light);
    }
}
