/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedmaawy
 */
public class SerialPortListener implements SerialPortEventListener {

    SerialPort serialPort;
    
    @Override
    public void serialEvent(SerialPortEvent spe) {
        SerialPort port = (SerialPort) spe.getSource();
        BufferedReader bufferedReader;
        
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(port.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(SerialPortListener.class.getName()).log(Level.SEVERE, null, ex);
            
            return;
        }
        
        switch(spe.getEventType()) {
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:
                String output = "";
                String readLine = "";
                
                try {
                    boolean stop = false;
                    
                    while(!stop) {
                        readLine = bufferedReader.readLine();
                        output = output.concat(readLine + "\r\n");
                        
                        if(readLine.contains("OK") || readLine.contains("ERROR") || readLine.trim().equals(">"))
                            stop = true;
                    }
                    
                    SerialPorts.setSerialPortReturnValue(output);
                    
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(SerialPortListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
        }
    }
    
}
