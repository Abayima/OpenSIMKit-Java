/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit;

import org.opensimkit.utilities.AnonymousDataCollection;
import org.opensimkit.utilities.SerialPorts;
import org.opensimkit.utilities.Bootstrap;

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
    
    public static void main(String[] args) {
        serialPorts = null;
        
        // Initialize the bootstrap sequence
        bootstrap = new Bootstrap();
        
        // Only run the application if the bootstrap was successful
        if(bootstrap.isBootstrapSuccessful())
        {
            anonymousDataCollection = new AnonymousDataCollection();
        
            mainFrame = new MainFrame();
            mainFrame.setTitle("OpenSIMKit");
            mainFrame.pack();
            mainFrame.setVisible(true);
        }
    }
}
