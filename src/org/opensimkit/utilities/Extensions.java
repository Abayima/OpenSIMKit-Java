/*
 * Deals with system wide extensions. 
 * Extensions are components that extend the system's functionality and are plugins
 */
package org.opensimkit.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import org.opensimkit.OpenSIMKit;
import org.opensimkit.extensions.ExtensionInterface;
import org.opensimkit.extensions.ExtensionParameter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ahmedmaawy
 */
public class Extensions {
    private PluginManager pm;
    private ArrayList <ExtensionInterface> availableExtensions;
    private ArrayList <ExtensionDetails> extensionDetails = new ArrayList<ExtensionDetails>();
    private String pathToSettingsFile;
    
    private final String settingsFileName = "ExtensionSettings.xml";
    
    private final String EXTENSION_SETTINGS_NODE = "extension_settings";
    private final String EXTENSION_NODE = "extension";
    private final String PARAM_NAME_NODE = "param_name";
    private final String PARAM_VALUE_NODE = "param_value";
    
    /**
     * Extensions constructor
     */
    
    private class ExtensionParameters
    {
        private String extensionName;
        private ArrayList<ExtensionParameter> extensionParameters = new ArrayList<ExtensionParameter>();

        public String getExtensionName() {
            return extensionName;
        }

        public void setExtensionName(String extensionName) {
            this.extensionName = extensionName;
        }

        public ArrayList<ExtensionParameter> getExtensionParameters() {
            return extensionParameters;
        }

        public void setExtensionParameters(ArrayList<ExtensionParameter> extensionParameters) {
            this.extensionParameters = extensionParameters;
        }
    }
    
    public Extensions()
    {
        pathToSettingsFile = StringUtil.trimRight(OpenSIMKit.bootstrap.getRootFolder(), '/');
        pathToSettingsFile = pathToSettingsFile.concat("/" + settingsFileName);
        
        pm = PluginManagerFactory.createPluginManager();
        pm.addPluginsFrom(new File(OpenSIMKit.bootstrap.getExtensionsFolder()).toURI());
        
        PluginManagerUtil pmu = new PluginManagerUtil(pm);
        availableExtensions = (ArrayList<ExtensionInterface>) pmu.getPlugins(ExtensionInterface.class);
        
        // Load extension settings from file
        checkIfSettingsFileExists();
        
        // Load extension details
        int numExtensions = availableExtensions.size();
        
        for(int extensionLoop = 0; extensionLoop < numExtensions; extensionLoop ++)
        {
            ExtensionDetails newExtension = new ExtensionDetails();
            
            newExtension.setExtensionName(availableExtensions.get(extensionLoop).getExtensionName());
            newExtension.setExtensionClass(availableExtensions.get(extensionLoop).getClass().toString());
            newExtension.setExtensionInterface(availableExtensions.get(extensionLoop));
            newExtension.setExtensionParameters(availableExtensions.get(extensionLoop).getParameters());
            
            extensionDetails.add(newExtension);
        }
    }
    
    /**
     * Loads settings from settings file
     * 
     * @return boolean
     */
    
    private boolean loadSettingsFromFile()
    {
        try 
        {
            // Open and parse XML
            File fXmlFile = new File(pathToSettingsFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            // Read XML
            NodeList extensionsList = doc.getElementsByTagName("settings");
            Node extensionsNode = extensionsList.item(0);
            NodeList childNodes = extensionsNode.getChildNodes();
            
            int numberOfNodes = childNodes.getLength();
            int numExtensions = availableExtensions.size();
            
            ArrayList<ExtensionParameters> extensionParameters = new ArrayList<ExtensionParameters>();
            
            // Loop through each node and organize the data
            int currentNodeCount = 0;
            
            for(int currentNode = 0; currentNode < numberOfNodes; currentNode += 3)
            {
                Node extensionNode = extensionsList.item(currentNode);
                
                String extensionName = childNodes.item(currentNodeCount).getTextContent();
                String paramName = childNodes.item(currentNodeCount + 1).getTextContent();
                String paramValue = childNodes.item(currentNodeCount + 2).getTextContent();
                
                currentNodeCount += 3;
                
                ExtensionParameter extensionParameter = new ExtensionParameter();
                extensionParameter.setParameterName(paramName);
                extensionParameter.setParameterValue(paramValue);
                
                int numExtensionParameters = extensionParameters.size();
                boolean extensionFound = false;
                
                for(int extensionParameterLoop = 0; extensionParameterLoop < numExtensionParameters;
                        extensionParameterLoop ++)
                {
                    if(extensionParameters.get(extensionParameterLoop).getExtensionName().equals(extensionName))
                    {
                        extensionFound = true;
                        extensionParameters.get(extensionParameterLoop).getExtensionParameters().add(extensionParameter);
                    }
                }
                
                if(!extensionFound)
                {
                    ExtensionParameters newExtensionParameters = new ExtensionParameters();
                    newExtensionParameters.setExtensionName(extensionName);
                    newExtensionParameters.getExtensionParameters().add(extensionParameter);
                    
                    extensionParameters.add(newExtensionParameters);
                }
            }
            
            int numExtensionParameters = extensionParameters.size();
            
            // Do the actual extension settings assignments now
            for(int parametersLoop = 0; parametersLoop < numExtensionParameters; parametersLoop ++)
            {
                for(int extensionLoop = 0; extensionLoop < numExtensions; extensionLoop ++)
                {
                    if(availableExtensions.get(extensionLoop).getExtensionName().equals(extensionParameters.get(parametersLoop).getExtensionName()))
                    {
                        availableExtensions.get(extensionLoop).setParameters(extensionParameters.get(parametersLoop).getExtensionParameters());
                    }
                }
            }
            
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }
    
    /**
     * Checks to see if the device settings file exists
     * 
     * @return boolean
     */
    
    private boolean checkIfSettingsFileExists()
    {
        File settingsFile = new File(pathToSettingsFile);
        
        // File exists ?
        if(settingsFile.exists()) {
            // Can we parse the XML for desired values ?
            return loadSettingsFromFile();
        }
        
        return false;
    }

    /**
     * Gets extension details
     * 
     * @return extension details
     */
    
    public ArrayList<ExtensionDetails> getExtensionDetails() {
        return extensionDetails;
    }
}
