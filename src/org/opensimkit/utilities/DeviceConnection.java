/*
 * Device connectivity utilities
 */
package org.opensimkit.utilities;

import org.opensimkit.OpenSIMKit;

/**
 *
 * @author ahmedmaawy
 */
public class DeviceConnection {
    public enum deviceConnectionMode
    {
        driver, serial_port
    }
    
    private deviceConnectionMode connectionMode;

    // Getters and setters
    
    public deviceConnectionMode getConnectionMode() {
        return connectionMode;
    }

    public void setConnectionMode(deviceConnectionMode connectionMode) {
        this.connectionMode = connectionMode;
    }
    
    // Connect via drivers
    
    public boolean connectViaDrivers()
    {
        // connectionMode = deviceConnectionMode.driver;
        return false;
    }
    
    // Connect via generic mode
    
    public boolean connectViaGeneric()
    {
        SerialPorts serialPorts = new SerialPorts();
        
        if(serialPorts.autoConnect())
        {
            OpenSIMKit.serialPorts = serialPorts;
            OpenSIMKit.mainFrame.setConnectedInterface();
            
            connectionMode = deviceConnectionMode.serial_port;
            
            return true;
        }
        
        return false;
    }
}
