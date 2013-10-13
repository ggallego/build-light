package com.buildlight.driver.trafficlight.device.dreamcheeky;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import com.buildlight.driver.trafficlight.api.Led;
import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.buildlight.driver.trafficlight.api.TrafficLightException;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

/**
 * @author ggallego
 */
public class DreamCheekyImplTest {

    @Mock
    private HIDManager hidManager;

    @Mock
    private HIDDevice hidDevice;

    private TrafficLight light;

    @Before
    public void setup() throws IOException {
        initMocks(this);
        light = new DreamCheekyImpl(hidManager);
        when(hidDevice.write(any(byte[].class))).thenReturn(4);
        ReflectionTestUtils.setField(light, "hidDevice", hidDevice );
    }

    @Test
    public void testSwitchOnAllLeds() throws Exception {
        light.switchOnAllLeds();
        verify(hidDevice, times(1)).write(any(byte[].class));
    }

    @Test
    public void testSwitchOnTheBlueLed() throws Exception {
        light.switchOn(Led.BLUE);
        verify(hidDevice, times(1)).write(new byte[]{0x01, 0x0, 0x0, 0x0});
    }

    @SuppressWarnings("unchecked")
	@Test(expected = TrafficLightException.class)
    public void testSwitchOnWithException() throws Exception {
        when(hidDevice.write(any(byte[].class))).thenThrow(IOException.class);
        light.switchOnAllLeds();
    }

    @Test
    public void testSwitchOff() throws Exception {
        light.switchOffAllLeds();
        verify(hidDevice, times(1)).write(new byte[]{0x00, 0x0, 0x0, 0x0});
    }

    @SuppressWarnings("unchecked")
	@Test(expected = TrafficLightException.class)
    public void testSwitchOffWithException() throws Exception {
        when(hidDevice.write(any(byte[].class))).thenThrow(IOException.class);
        light.switchOffAllLeds();
    }

    @Test
    public void testClose() throws Exception {
        light.close();
        verify(hidDevice, times(1)).close();
        verify(hidManager, times(1)).release();
    }

    @Test(expected = TrafficLightException.class)
    public void testCloseWithException() throws Exception {
        doThrow(IOException.class).when(hidDevice).close();
        light.close();
    }
}
