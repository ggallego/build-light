package com.buildlight.driver.trafficlight.api;

/**
 * @author zutherb
 * @author ggallego
 */

// DreamCheeky Values
// R G B Color Value
// 0 0 0 off 0x00
// 0 0 1 blue 0x01
// 1 0 0 red 0x02
// 0 1 0 green 0x03
// 0 1 1 aqua 0x04
// 1 0 1 purple 0x05
// 1 1 0 yellow 0x06 (more of a mustard - green color)
// 1 1 1 white 0x07 (a very blue-ish white)

public enum Led {
	
    BLUE((byte) 0x00, (byte) 0x01),
    RED((byte) 0x10, (byte) 0x02),
    GREEN((byte) 0x12, (byte) 0x03),
    AQUA((byte) 0x00, (byte) 0x04),
    PURPLE((byte) 0x00, (byte) 0x05),
    YELLOW((byte) 0x11, (byte) 0x06),
    WHITE((byte) 0x00, (byte) 0x07);

    private byte clewareAdress;
    private byte dreamCheekyAdress;
    
    Led(byte clewareAdress, byte dreamCheekyAdress) {
        this.clewareAdress = clewareAdress;
        this.dreamCheekyAdress = dreamCheekyAdress;
    }

    public byte getAddress(Device device) {
    	switch (device) {
		case CLEWARE:
			return clewareAdress;
		case DREAMCHEEKY:
			return dreamCheekyAdress;
		default:
			break;
		}
        return (byte) 0x00;
    }

    public static Led valueOfIgnoreCaseOrNull(String name) {
        for (Led led : Led.values()) {
            if (led.name().equalsIgnoreCase(name)) {
                return led;
            }
        }
        return null;
    }
}
