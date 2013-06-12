/*
 * Device connectivity utilities
 */
package org.opensimkit.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.opensimkit.OpenSIMKit;
import org.opensimkit.drivers.DriverInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author ahmedmaawy
 */
public class DeviceConnection {
    
    public enum deviceConnectionMode
    {
        driver, serial_port
    }
    
    private class DeviceXMLProfile
    {
        private String deviceManufacturer;
        private String deviceModel;
        private boolean deviceGeneric;
        private String devicePortName;
        private String deviceClassName;
        private boolean populated;
        
        public DeviceXMLProfile()
        {
            populated = false;
        }

        public String getDeviceManufacturer() {
            return deviceManufacturer;
        }

        public void setDeviceManufacturer(String deviceManufacturer) {
            this.deviceManufacturer = deviceManufacturer;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public boolean isDeviceGeneric() {
            return deviceGeneric;
        }

        public void setDeviceGeneric(boolean deviceGeneric) {
            this.deviceGeneric = deviceGeneric;
        }

        public String getDevicePortName() {
            return devicePortName;
        }

        public void setDevicePortName(String devicePortName) {
            this.devicePortName = devicePortName;
        }

        public String getDeviceClassName() {
            return deviceClassName;
        }

        public void setDeviceClassName(String deviceClassName) {
            this.deviceClassName = deviceClassName;
        }

        public boolean isPopulated() {
            return populated;
        }

        public void setPopulated(boolean populated) {
            this.populated = populated;
        }
    }
    
    private class PortIdentification
    {
        private String portName;
        private int portIndex;

        public String getPortName() {
            return portName;
        }

        public void setPortName(String portName) {
            this.portName = portName;
        }

        public int getPortIndex() {
            return portIndex;
        }

        public void setPortIndex(int portIndex) {
            this.portIndex = portIndex;
        }
    }
    
    private deviceConnectionMode connectionMode;
    private DeviceXMLProfile deviceXMLProfile;
    private DriverInterface currentDriver;
    
    private final String deviceSettingsFileName = "DeviceSettings.xml";
    private String pathToDeviceSettingsFile = "";
    private boolean deviceSettingsExist;
    private boolean deviceConnected;
    
    private final String DEVICE_MANUFACTURER = "Manufacturer";
    private final String DEVICE_MODEL = "Model";
    private final String DEVICE_GENERIC = "Generic";
    private final String DEVICE_CLASS_NAME = "Class";
    private final String DEVICE_PORT_NAME = "Port_Name";
    
    public DeviceConnection()
    {
        deviceConnected = false;
        
        pathToDeviceSettingsFile = 
                OpenSIMKit.bootstrap.getRootFolder().concat(deviceSettingsFileName);
        deviceXMLProfile = new DeviceXMLProfile();
        deviceSettingsExist = checkIfDeviceSettingsFileExists();
    }
    
    private boolean readDeviceXMLFile() 
    {
        try 
        {
            // Open and parse XML
            File fXmlFile = new File(pathToDeviceSettingsFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            // Read XML
            NodeList manufacturerList = doc.getElementsByTagName(this.DEVICE_MANUFACTURER);
            NodeList deviceModelList = doc.getElementsByTagName(this.DEVICE_MODEL);
            NodeList deviceGenericList = doc.getElementsByTagName(this.DEVICE_GENERIC);
            NodeList deviceClassNameList = doc.getElementsByTagName(this.DEVICE_CLASS_NAME);
            NodeList devicePortNameList = doc.getElementsByTagName(this.DEVICE_PORT_NAME);
            
            String manufacturerValue = manufacturerList.item(0).getTextContent();
            String modelValue = deviceModelList.item(0).getTextContent();
            String deviceGenericValue = deviceGenericList.item(0).getTextContent();
            String deviceClassNameValue = deviceClassNameList.item(0).getTextContent();
            String devicePortNameValue = devicePortNameList.item(0).getTextContent();
            
            boolean deviceIsGeneric = Boolean.parseBoolean(deviceGenericValue);
            
            // Populate class wide device settings profile
            deviceXMLProfile.setDeviceManufacturer(manufacturerValue);
            deviceXMLProfile.setDeviceModel(modelValue);
            deviceXMLProfile.setDeviceGeneric(deviceIsGeneric);
            deviceXMLProfile.setDevicePortName(devicePortNameValue);
            deviceXMLProfile.setDeviceClassName(deviceClassNameValue);
            deviceXMLProfile.setPopulated(true);
        } catch (Exception ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        
        return true;
    }
    
    // Save device XML details
    
    public boolean saveDeviceXMLSettings(String manufacturer, String model, boolean generic,
        String className, String portName)
    {
        try 
        {
            if(manufacturer == null)
                manufacturer = "";
            if(model == null)
                model = "";
            if(className == null)
                className = "";
            if(portName == null)
                portName = "";
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("settings");
            doc.appendChild(rootElement);
            
            // Perpare content elements for XML
            Element xmlManufacturer = doc.createElement(this.DEVICE_MANUFACTURER);
            xmlManufacturer.appendChild(doc.createTextNode(manufacturer));
            rootElement.appendChild(xmlManufacturer);
            
            Element xmlModel = doc.createElement(this.DEVICE_MODEL);
            xmlModel.appendChild(doc.createTextNode(model));
            rootElement.appendChild(xmlModel);
            
            Element xmlGeneric = doc.createElement(this.DEVICE_GENERIC);           
            if(generic) {
                xmlGeneric.appendChild(doc.createTextNode("true"));
            }
            else {
                xmlGeneric.appendChild(doc.createTextNode("false"));
            }
            rootElement.appendChild(xmlGeneric);
            
            Element xmlClassName = doc.createElement(this.DEVICE_CLASS_NAME);
            xmlClassName.appendChild(doc.createTextNode(className));
            rootElement.appendChild(xmlClassName);
            
            Element xmlPortName = doc.createElement(this.DEVICE_PORT_NAME);
            xmlPortName.appendChild(doc.createTextNode(portName));
            rootElement.appendChild(xmlPortName);
            
            // Write the content into xml file
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            StreamResult result = new StreamResult(new File(pathToDeviceSettingsFile));

            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        } catch (TransformerException ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        
        return true;
    }
    
    private boolean checkIfDeviceSettingsFileExists()
    {
        File settingsFile = new File(pathToDeviceSettingsFile);
        
        // File exists ?
        if(settingsFile.exists()) {
            // Can we parse the XML for desired values ?
            return readDeviceXMLFile();
        }
        
        return false;
    }

    // Getters and setters
    
    public deviceConnectionMode getConnectionMode()
    {
        return connectionMode;
    }

    public void setConnectionMode(deviceConnectionMode connectionMode)
    {
        this.connectionMode = connectionMode;
    }
    
    // Loop through all devices getting device details to connect to appropriate driver
    
    private PortIdentification searchForDevices(String manufacturer, String model, 
        String revision)
    {
        return null;
    }
    
    // Connect via drivers
    
    public boolean connectViaDrivers()
    {
        deviceConnected = false;
        
        ArrayList <DriverDetails> availableDrivers = OpenSIMKit.drivers.getDriverDetails();
        int numDrivers = availableDrivers.size();
        
        boolean connectionStatus = false;
        
        if(deviceXMLProfile.isPopulated())
        {
            // Load from the port name
            if(deviceXMLProfile.isDeviceGeneric())
            {
                // Generic driver functionality
                boolean driverStatus = false;
                
                String className = deviceXMLProfile.getDeviceClassName();
                
                for(int driverLoop = 0; driverLoop < numDrivers; driverLoop ++)
                {
                    if(availableDrivers.get(driverLoop).getDriverClass().equals(className))
                    {
                        // We got the driver
                        currentDriver = availableDrivers.get(driverLoop).getDriverInterface();
                        driverStatus = true;
                    }
                }
                
                if(driverStatus)
                {
                    connectionStatus = currentDriver.connectToDevice();
                }
            }
            
            if(connectionStatus)
            {
                connectionMode = deviceConnectionMode.driver;
                deviceConnected = true;
                OpenSIMKit.mainFrame.setConnectedInterface();
                
                return true;
            }    
        }
        else
        {
            // Device profile not populated, search for appropriate device
            for(int driverLoop = 0; driverLoop < numDrivers; driverLoop ++)
            {
                DriverInterface currentLoopDriver = availableDrivers.get(driverLoop).getDriverInterface();
                
                if(currentLoopDriver.isGenericConnection())
                {
                    // Logic for a generic driver
                    if(currentLoopDriver.connectToDevice())
                    {
                        currentDriver = currentLoopDriver;
                        connectionMode = deviceConnectionMode.driver;
                        
                        // Save connection settings for reference the next time
                        saveDeviceXMLSettings(currentLoopDriver.getManufacturer(), 
                                currentLoopDriver.getModel(), 
                                currentLoopDriver.isGenericConnection(), 
                                currentLoopDriver.getClass().getName(), 
                                currentLoopDriver.getPortName());
                        
                        deviceConnected = true;
                        OpenSIMKit.mainFrame.setConnectedInterface();
                        
                        return true;
                    }
                }
                else
                {
                    // Logic for a serial port driven device
                    String manufacturer = currentLoopDriver.getManufacturer();
                    String model = currentLoopDriver.getModel();
                    String revision = currentLoopDriver.getRevision();
                    
                    PortIdentification portIdentification = searchForDevices(manufacturer, model, revision);
                    
                    if(portIdentification != null)
                    {
                        int portIndex = portIdentification.getPortIndex();
                        String portName = portIdentification.getPortName();
                        
                        if(currentLoopDriver.connectToSerialPort(portIndex))
                        {
                            currentDriver = currentLoopDriver;
                            connectionMode = deviceConnectionMode.driver;

                            // Save connection settings for reference the next time
                            saveDeviceXMLSettings(currentLoopDriver.getManufacturer(), 
                                    currentLoopDriver.getModel(), 
                                    currentLoopDriver.isGenericConnection(), 
                                    currentLoopDriver.getClass().getName(), 
                                    currentLoopDriver.getPortName());
                            
                            deviceConnected = true;
                            OpenSIMKit.mainFrame.setConnectedInterface();

                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    // Connect via generic mode
    
    public boolean connectViaGeneric()
    {
        deviceConnected = false;
        
        SerialPorts serialPorts = new SerialPorts();
        
        if(serialPorts.autoConnect())
        {
            OpenSIMKit.serialPorts = serialPorts;
            OpenSIMKit.mainFrame.setConnectedInterface();
            
            connectionMode = deviceConnectionMode.serial_port;
            deviceConnected = true;
            
            return true;
        }
        
        return false;
    }
    
    public boolean disconnectDevice()
    {
        if(!deviceConnected)
            return false;
        
        if(connectionMode == deviceConnectionMode.driver)
        {
            return currentDriver.disconnectDevice();
        }
        else if(connectionMode == deviceConnectionMode.serial_port)
        {
            return OpenSIMKit.serialPorts.disconnectPort();
        }
        
        return false;
    }

    public boolean isDeviceConnected() {
        return deviceConnected;
    }

    public boolean isDeviceSettingsExist() {
        return deviceSettingsExist;
    }
    
    public DriverInterface getCurrentDriver() {
        return currentDriver;
    }
}
