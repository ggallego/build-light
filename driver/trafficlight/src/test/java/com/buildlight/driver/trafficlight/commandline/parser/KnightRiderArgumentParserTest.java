package com.buildlight.driver.trafficlight.commandline.parser;

import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.buildlight.driver.trafficlight.commandline.parser.ArgumentBuffer;
import com.buildlight.driver.trafficlight.commandline.parser.KnightRiderArgumentParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class KnightRiderArgumentParserTest {

    @Mock
    private TrafficLight light;

    @InjectMocks
    private KnightRiderArgumentParser parser = new KnightRiderArgumentParser(light);

    private ArgumentBuffer buffer;

    @Before
    public void setup() {
        initMocks(this);
        parser.setExecutionTime(50);
        parser.setSleepTime(1);

        buffer = new ArgumentBuffer(new String[]{"--knightrider"});
        buffer.next();
    }

    @Test
    public void isResponsibleTrue() {
        assertTrue(parser.isResponsible(buffer));
    }

    @Test
    public void isResponsibleFalse() {
        assertFalse(parser.isResponsible(new ArgumentBuffer(new String[]{"--gui"})));
    }

    @Test
    public void testExecute() throws Exception {
        parser.execute(buffer);
        verify(light, atLeastOnce()).switchOn(Led.RED);
        verify(light, atLeastOnce()).switchOn(Led.YELLOW);
        verify(light, atLeastOnce()).switchOn(Led.GREEN);
        verify(light, atLeast(5)).switchOffAllLeds();
    }
}
