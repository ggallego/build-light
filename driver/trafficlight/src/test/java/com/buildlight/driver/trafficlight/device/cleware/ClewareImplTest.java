package com.buildlight.driver.trafficlight.device.cleware;

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

import com.buildlight.driver.trafficlight.api.TrafficLight;
import com.buildlight.driver.trafficlight.api.TrafficLightException;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

/**
 * @author zutherb
 */
public class ClewareImplTest {

    @Mock
    private HIDManager hidManager;

    @Mock
    private HIDDevice hidDevice;

    private TrafficLight light;

    @Before
    public void setup() throws IOException {
        initMocks(this);
        light = new ClewareImpl(hidManager);
        when(hidDevice.write(any(byte[].class))).thenReturn(4);
        ReflectionTestUtils.setField(light, "hidDevice", hidDevice );
    }

    @Test
    public void testSwitchOn() throws Exception {
        light.switchOnAllLeds();
        verify(hidDevice, times(1)).write(new byte[]{0x0, 0x0, 0x10, 1});
        verify(hidDevice, times(1)).write(new byte[]{0x0, 0x0, 0x11, 1});
        verify(hidDevice, times(1)).write(new byte[]{0x0, 0x0, 0x12, 1});
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
        verify(hidDevice, times(1)).write(new byte[]{0x0, 0x0, 0x10, 0});
        verify(hidDevice, times(1)).write(new byte[]{0x0, 0x0, 0x11, 0});
        verify(hidDevice, times(1)).write(new byte[]{0x0, 0x0, 0x12, 0});
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
