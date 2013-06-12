/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import java.util.ArrayList;

/**
 *
 * @author ahmedmaawy
 */
public class Messages {
    ArrayList<String> messages = new ArrayList<String>();
    
    /**
     * Constructor
     * 
     * @param textToProcess 
     */
    
    public Messages(String textToProcess)
    {
        String [] splitString = textToProcess.split("\n");
        int numElements = splitString.length;
        
        for(int elementLoop = 0; elementLoop < numElements; elementLoop ++)
        {
            if(!splitString[elementLoop].contains("+CMGL") 
                    && !splitString[elementLoop].trim().equals("OK")
                    && !splitString[elementLoop].trim().equals(""))
            {
                messages.add(splitString[elementLoop].trim());
            }
        }
    }
    /**
     * Constructor: Ideal for device drivers
     * 
     * @param textToProcess
     * @param delimiter 
     */
    
    public Messages(String textToProcess, String delimiter)
    {
        String [] splitString = textToProcess.split(delimiter);
        int numElements = splitString.length;
        
        for(int elementLoop = 0; elementLoop < numElements; elementLoop ++)
        {
            messages.add(splitString[elementLoop].trim());
        }
    }
    
    /**
     * Get messages
     * 
     * @return 
     */

    public ArrayList<String> getMessages() {
        return messages;
    }
}
