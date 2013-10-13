package com.buildlight.driver.trafficlight.api;

/**
 * @author ggallego
 */

public enum Device {

    CLEWARE(0xD50,0x8),				// Cleware Vendor Id - Traffic Light Product Id
    DREAMCHEEKY(0x1294, 0x1320),	// RISO KAGAKU CORP - Webmail Window
    MOCK(0x000,0x000);

    private int vendorId;
    private int productId;
    
    Device(int vendorId, int productId) {
        this.vendorId = vendorId;
        this.productId = productId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getProductId() {
        return productId;
    }

    public static Device valueOfIgnoreCaseOrNull(String name) {
        for (Device device : Device.values()) {
            if (device.name().equalsIgnoreCase(name)) {
                return device;
            }
        }
        return null;
    }

}
