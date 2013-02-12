/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit;

import org.opensimkit.utilities.SerialPorts;

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
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        serialPorts = null;
        
        mainFrame = new MainFrame();
        mainFrame.setTitle("OpenSIMKit");
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
