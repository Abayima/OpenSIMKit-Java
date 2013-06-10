/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit;

import org.opensimkit.utilities.AnonymousDataCollection;
import org.opensimkit.utilities.SerialPorts;
import org.opensimkit.utilities.Bootstrap;
import org.opensimkit.utilities.Drivers;
import org.opensimkit.utilities.Extensions;

/**
 *
 * @author ahmedmaawy
 */
public class OpenSIMKit {

    /**
     * @param args the command line arguments
     */
    
    public static MainFrame mainFrame;
    public static SerialPorts serialPorts;
    public static AnonymousDataCollection anonymousDataCollection;
    public static Bootstrap bootstrap;
    public static Drivers drivers;
    public static Extensions extensions;
    
    public static void main(String[] args) {
        serialPorts = null;
        
        // Initialize the bootstrap sequence
        bootstrap = new Bootstrap();
        
        // Only run the application if the bootstrap was successful
        if(bootstrap.isBootstrapSuccessful())
        {
            // Load anonymous data collection mechanism
            anonymousDataCollection = new AnonymousDataCollection();
            
            // Load drivers
            drivers = new Drivers();
            
            // Load extensions
            extensions = new Extensions();
        
            mainFrame = new MainFrame();
            mainFrame.setTitle("OpenSIMKit");
            mainFrame.pack();
            mainFrame.setVisible(true);
        }
    }
}
