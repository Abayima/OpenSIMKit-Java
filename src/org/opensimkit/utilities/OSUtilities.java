/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

/**
 *
 * @author ahmedmaawy
 */
public class OSUtilities {
    
    private String osName = "";
    
    /**
     * Constructor
     * 
     * @param osName 
     */
    
    public OSUtilities(String osName)
    {
        this.osName = osName;
    }
    
    /**
     * Determine if it is Windows
     * 
     * @return boolean
     */
    
    public boolean isWindows() {
        return (osName.indexOf("win") >= 0);
    }
    
    /**
     * Determine if it is Mac
     * 
     * @return boolean
     */
    
    public boolean isMac() {
        return (osName.indexOf("mac") >= 0);
    }
    
    /**
     * Determine if it is Unix
     * 
     * @return boolean
     */
    
    public boolean isUnix() {
        return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") >= 0);
    }
    
    /**
     * Determine if it is Solaris
     * 
     * @return boolean
     */
    
    public boolean isSolaris() {
        return (osName.indexOf("sunos") >= 0);
    }
}
