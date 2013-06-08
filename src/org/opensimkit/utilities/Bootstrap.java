/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedmaawy
 */
public class Bootstrap {
    private String rootFolder;
    private String pluginsFolder;
    private String driversFolder;
    
    private boolean bootstrapSuccessful;
    
    public final String pluginsFolderName = "plugins";
    public final String driversFolderName = "drivers";

    // Getters
    
    public String getRootFolder() {
        return rootFolder;
    }

    public String getPluginsFolder() {
        return pluginsFolder;
    }

    public String getDriversFolder() {
        return driversFolder;
    }

    public boolean isBootstrapSuccessful() {
        return bootstrapSuccessful;
    }
    
    // Utility functions
    
    private boolean setFolders()
    {
        String path = Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
        try {
            String binaryPath = URLDecoder.decode(path, "UTF-8");
            
            String pathToFile = StringUtil.trimRight(binaryPath, '/');
            
            int jarNameIndex = pathToFile.indexOf("/OpenSIMKit.jar");
            
            if(jarNameIndex > -1)
            {
                pathToFile = StringUtil.trimRight((pathToFile.substring(0, jarNameIndex)), '/');
            }
            
            rootFolder = pathToFile.concat("/");
            pluginsFolder = rootFolder.concat(pluginsFolderName.concat("/"));
            driversFolder = pluginsFolder.concat(driversFolderName.concat("/"));
            
            return true;
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
    private boolean doBootstrap()
    {
        bootstrapSuccessful = setFolders();
        return bootstrapSuccessful;
    }
    
    public Bootstrap()
    {
        doBootstrap();
    }
}
