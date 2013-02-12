/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

/**
 *
 * @author ahmedmaawy
 */

import java.util.Enumeration;
import java.util.ArrayList;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialPorts {
    private ArrayList<CommPortIdentifier> serialPorts;
    private ArrayList<String> serialPortList;
    private SerialPort serialPort;
    private boolean connected = false;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private PrintStream printStream;
    
    // Returned by the event handler
    public static String serialPortReturnValue;
    
    // Constants
    final char CTRL_Z = (char)26;
    final String CMD_CHECK_CONNECTION = "AT\r\n";
    final String CMD_SET_TEXT_MODE_FORMAT = "AT+CMGF=1\r\n";
    final String CMD_CHAR_SET_PCCP347 = "AT+CSCS=\"PCCP437\"\r\n";
    final String CMD_SELECT_SIM_STORAGE = "AT+CPMS=\"SM\"\r\n";
    final String CMD_READ_ALL_MESSAGES = "AT+CMGL=\"ALL\"\r\n";
    final String CMD_READ_MESSAGE = "AT+CMGR={{ message_index }}\r\n";
    final String CMD_DETAILED_ERRORS = "AT+CMEE=1";

    // Full write command
    final String CMD_WRITE_MESSAGE_TO_MEMORY = "AT+CMGW=\"{{ message_contact }}\"\r{{ message }}";

    // Parial write command
    final String CMD_REQUEST_WRITE_MESSAGE = "AT+CMGW=\"{{ message_contact }}\"\r\n";
    final String CMD_DO_WRITE_AFTER_REQUEST = "{{ message }}";
    
    /**
     * Constructor
     */
    
    public SerialPorts()
    {
        serialPortList = new ArrayList<String>();
        serialPorts = new ArrayList<CommPortIdentifier>();
        
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        
        while(portList.hasMoreElements())
        {
            CommPortIdentifier cpi = (CommPortIdentifier) portList.nextElement();
            
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                serialPorts.add(cpi);
                serialPortList.add(cpi.getName());
            } else if (cpi.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                // Do nothing at the moment
            } else {
                // Unknown port
            }
        }
    }
    
    /**
     * Connect to a serial port
     * 
     * @param portIndex
     * 
     * @return boolean
     */
    
    public boolean connectPort(int portIndex)
    {
        if(!connected) {
            try {
                serialPort = (SerialPort)serialPorts.get(portIndex).open("OpenSIMKit", 2000);
                try {
                    
                    serialPort.setSerialPortParams(9600, 
                            SerialPort.DATABITS_8, 
                            SerialPort.STOPBITS_1, 
                            SerialPort.PARITY_NONE);
                    
                    serialPort.addEventListener(new SerialPortListener());
                    serialPort.notifyOnDataAvailable(true);
                    serialPort.notifyOnOutputEmpty(true);
                    
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    
                    bufferedReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                    printStream = new PrintStream(serialPort.getOutputStream(), true);
                    
                    setTextFormat();
                    setMemSIMCard();
                } catch (UnsupportedCommOperationException ex) {
                    connected = false;
                    Logger.getLogger(SerialPorts.class.getName()).log(Level.SEVERE, null, ex);
                    
                    return false;
                } catch (TooManyListenersException ex) {
                    connected = false;
                    Logger.getLogger(SerialPorts.class.getName()).log(Level.SEVERE, null, ex);
                    
                    return false;
                    
                } catch (IOException ex) {
                    connected = false;
                    Logger.getLogger(SerialPorts.class.getName()).log(Level.SEVERE, null, ex);
                    
                    return false;
                }
            } catch (PortInUseException ex) {
                connected = false;
                Logger.getLogger(SerialPorts.class.getName()).log(Level.SEVERE, null, ex);

                return false;
            }

            connected = true;
        }
        else {
            return false;
        }
        
        return true;
    }
    
    /**
     * Disconnect the port
     * 
     * @return boolean
     */
    
    public boolean disconnectPort()
    {
        // TODO: Investigate why an exception occurs when closing the port
        
        if(connected) {
            new Thread(){
                @Override
                public void run()
                {
                    serialPort.removeEventListener();
                    serialPort.close();
                }
            }.start();
        }
        
        return true;
    }
    
    private String readPort()
    {
        String output = "";
        String readLine = "";
        
        try {
            while((readLine = bufferedReader.readLine()) != null) {
                output.concat("\r\n" + readLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(SerialPorts.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
        
        return output;
    }
    
    /**
     * Sets the format to text format
     */
    
    private boolean setTextFormat()
    {
        printStream.print(CMD_SET_TEXT_MODE_FORMAT);
        
        return true;
    }
    
    /**
     * Sets the storage to SIM card storage
     */
    
    private boolean setMemSIMCard()
    {
        printStream.print(CMD_SELECT_SIM_STORAGE);
        
        return true;
    }
    
    /**
     * Runs a custom command
     * 
     * @param command
     * @return 
     */
    
    private void runCommand(String command)
    {
        printStream.print(command);
    }
    
    /**
     * Get all messages in the system
     * 
     * @return String
     */
    
    public String getAllMessages()
    {
        runCommand(CMD_READ_ALL_MESSAGES);
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialPorts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return serialPortReturnValue;
    }
    
    /**
     * Save a message
     * 
     * @param message
     * @return 
     */
    
    public boolean saveMessage(String contact, String message)
    {
        String requestWriteCommand = CMD_REQUEST_WRITE_MESSAGE.replace("{{ message_contact }}", contact);
        runCommand(requestWriteCommand);
        
        if(!serialPortReturnValue.contains(">")) {
            String messageToWrite = (CMD_WRITE_MESSAGE_TO_MEMORY.replace("{{ message }}", message)).concat(String.valueOf(CTRL_Z));
            runCommand(messageToWrite);
            
            return true;
        }
        
        return false;
    }

    /**
     * getSerialPortList()
     * 
     * @return List of Serial Port Names
     */
    
    public ArrayList<String> getSerialPortList() {
        return serialPortList;
    }

    /**
     * Checks to see if the serial port is connected
     * 
     * @return 
     */
    
    public boolean isConnected() {
        return connected;
    }
}
