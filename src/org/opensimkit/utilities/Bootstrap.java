/*
 * Application bootstrap
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import java.io.File;
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
    private String extensionsFolder;
    
    private boolean bootstrapSuccessful;
    
    public final String pluginsFolderName = "plugins";
    public final String driversFolderName = "drivers";
    public final String extensionsFolderName = "extensions";

    // Getters
    
    /**
     * Get the root folder path
     * 
     * @return Root folder path
     */
    
    public String getRootFolder() {
        return rootFolder;
    }
    
    /**
     * Get plugins folder path
     * 
     * @return plugins folder path
     */

    public String getPluginsFolder() {
        return pluginsFolder;
    }
    
    /**
     * Get drivers folder path
     * 
     * @return drivers folder path
     */

    public String getDriversFolder() {
        return driversFolder;
    }
    
    /**
     * Get extensions folder path
     * 
     * @return extensions folder path
     */

    public String getExtensionsFolder() {
        return extensionsFolder;
    }
    
    /**
     * Was the boostrap successful?
     * 
     * @return boolean 
     */
    
    public boolean isBootstrapSuccessful() {
        return bootstrapSuccessful;
    }
    
    // Utility functions
    
    /**
     * Set folder paths for various system folders
     * 
     * @return boolean success
     */
    
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
            extensionsFolder = pluginsFolder.concat(extensionsFolderName.concat("/"));
            
            return true;
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
    // Create a single folder
    
    /**
     * Create a folder if it dont exist
     * 
     * @param folderName
     * @return boolean success 
     */
    
    private boolean createFolder(File folderName)
    {
        if(!folderName.exists())
        {
            return folderName.mkdirs();
        }
        else
        {
            if(!folderName.isDirectory())
                return folderName.mkdirs();
            
            return true;
        }
    }
    
    // Creates needed folders
    
    /**
     * Checks availability for system folders and creates them
     * 
     * @return boolean success
     */
    
    private boolean checkAndCreateFolders()
    {
        if(isBootstrapSuccessful())
        {
            File driversFile = new File(driversFolder);
            File extensionsFile = new File(extensionsFolder);
            
            boolean driverStatus = createFolder(driversFile);
            boolean extensionsStatus = createFolder(extensionsFile);
            
            return (driverStatus && extensionsStatus);
        }
        
        return false;
    }
    
    /**
     * Does the main boot strapping
     * 
     * @return 
     */
    
    private boolean doBootstrap()
    {
        bootstrapSuccessful = setFolders();
        
        // Create required plugin folders
        
        if(bootstrapSuccessful)
        {
            boolean folderStatus = checkAndCreateFolders();
            return folderStatus;
        }
        
        return false;
    }
    
    /**
     * Constructor
     */
    
    public Bootstrap()
    {
        doBootstrap();
    }
}
