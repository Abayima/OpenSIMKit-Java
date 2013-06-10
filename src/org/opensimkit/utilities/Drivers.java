/*
 * Deals with system wide drivers. 
 * Drivers are components that deal with device specific logic and are plugins
 */
package org.opensimkit.utilities;

import java.io.File;
import java.util.ArrayList;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import org.opensimkit.OpenSIMKit;
import org.opensimkit.drivers.DriverInterface;

/**
 *
 * @author ahmedmaawy
 */
public class Drivers {
    private PluginManager pm;
    ArrayList <DriverInterface> availablePlugins;
    ArrayList <DriverDetails> driverDetails = new ArrayList<DriverDetails>();
    
    public Drivers()
    {
        pm = PluginManagerFactory.createPluginManager();
        pm.addPluginsFrom(new File(OpenSIMKit.bootstrap.getDriversFolder()).toURI());
        
        PluginManagerUtil pmu = new PluginManagerUtil(pm);
        availablePlugins = (ArrayList<DriverInterface>) pmu.getPlugins(DriverInterface.class);
        
        int numDrivers = availablePlugins.size();
        
        for(int driverLoop = 0; driverLoop < numDrivers; driverLoop ++)
        {
            DriverDetails newDriver = new DriverDetails();
            
            newDriver.setManufacturer(availablePlugins.get(driverLoop).getManufacturer());
            newDriver.setModel(availablePlugins.get(driverLoop).getModel());
            newDriver.setRevision(availablePlugins.get(driverLoop).getRevision());
            newDriver.setDriverClass(availablePlugins.get(driverLoop).getClass().toString());
            newDriver.setDelimiter(availablePlugins.get(driverLoop).getDelimiter());
            
            driverDetails.add(newDriver);
        }
    }

    public ArrayList<DriverDetails> getDriverDetails() {
        return driverDetails;
    }
}
