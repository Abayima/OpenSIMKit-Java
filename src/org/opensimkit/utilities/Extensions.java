/*
 * Deals with system wide extensions. 
 * Extensions are components that extend the system's functionality and are plugins
 */
package org.opensimkit.utilities;

import java.io.File;
import java.util.ArrayList;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import org.opensimkit.OpenSIMKit;
import org.opensimkit.extensions.ExtensionInterface;

/**
 *
 * @author ahmedmaawy
 */
public class Extensions {
    private PluginManager pm;
    ArrayList <ExtensionInterface> availableExtensions;
    ArrayList <ExtensionDetails> extensionDetails = new ArrayList<ExtensionDetails>();
    
    public Extensions()
    {
        pm = PluginManagerFactory.createPluginManager();
        pm.addPluginsFrom(new File(OpenSIMKit.bootstrap.getDriversFolder()).toURI());
        
        PluginManagerUtil pmu = new PluginManagerUtil(pm);
        availableExtensions = (ArrayList<ExtensionInterface>) pmu.getPlugins(ExtensionInterface.class);
        
        int numExtensions = availableExtensions.size();
        
        for(int driverLoop = 0; driverLoop < numExtensions; driverLoop ++)
        {
            ExtensionDetails newExtension = new ExtensionDetails();
            
            newExtension.setExtensionClass(availableExtensions.get(driverLoop).getClass().toString());
            newExtension.setExtensionInterface(availableExtensions.get(driverLoop));
            
            extensionDetails.add(newExtension);
        }
    }

    public ArrayList<ExtensionDetails> getExtensionDetails() {
        return extensionDetails;
    }
}
