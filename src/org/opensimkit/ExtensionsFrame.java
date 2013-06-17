/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdesktop.layout.GroupLayout;
import org.opensimkit.extensions.ExtensionParameter;
import org.opensimkit.utilities.AnonymousDataCollection;
import org.opensimkit.utilities.ExtensionDetails;
import org.opensimkit.utilities.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author ahmedmaawy
 */
public class ExtensionsFrame extends javax.swing.JFrame {

    /**
     * Creates new form ExtensionsFrame
     */
    
    private class SettingsControl
    {
        private String setting;
        private JTextField textField;

        public String getSetting() {
            return setting;
        }

        public void setSetting(String setting) {
            this.setting = setting;
        }

        public JTextField getTextField() {
            return textField;
        }

        public void setTextField(JTextField textField) {
            this.textField = textField;
        }
    }
    
    private class ExtensionSettingsControls
    {
        private ExtensionDetails extensionDetails;
        private ArrayList <SettingsControl> settingsControl;

        public ExtensionDetails getExtensionDetails() {
            return extensionDetails;
        }

        public void setExtensionDetails(ExtensionDetails extensionDetails) {
            this.extensionDetails = extensionDetails;
        }

        public ArrayList<SettingsControl> getSettingsControl() {
            return settingsControl;
        }

        public void setSettingsControl(ArrayList<SettingsControl> settingsControl) {
            this.settingsControl = settingsControl;
        }
    }
    
    private final String EXTENSION_SETTINGS_NODE = "extension_settings";
    private final String EXTENSION_NODE = "extension";
    private final String PARAM_NAME_NODE = "param_name";
    private final String PARAM_VALUE_NODE = "param_value";
    private final String settingsFileName = "ExtensionSettings.xml";
    private String pathToSettingsFile = "";
    
    private ArrayList <ExtensionSettingsControls> extensionSettingsControls = new ArrayList<ExtensionSettingsControls>();
    
    public ExtensionsFrame() {
        initComponents();
        
        pathToSettingsFile = StringUtil.trimRight(OpenSIMKit.bootstrap.getRootFolder(), '/');
        pathToSettingsFile = pathToSettingsFile.concat("/" + settingsFileName);
        
        ArrayList<ExtensionDetails> extensionDetails = OpenSIMKit.extensions.getExtensionDetails();
        int numExtensions = extensionDetails.size();
        
        for(int extensionLoop = 0; extensionLoop < numExtensions; extensionLoop ++)
        {
            // Build the panel
            JPanel extensionPanel = new JPanel();
            GroupLayout panelGroupLayout = new GroupLayout(extensionPanel);
            GroupLayout.SequentialGroup sequentialLayout = panelGroupLayout.createSequentialGroup();
            GroupLayout.ParallelGroup parallelLayout = panelGroupLayout.createParallelGroup();
            extensionPanel.setLayout(panelGroupLayout);
            panelGroupLayout.setHorizontalGroup(parallelLayout);
            panelGroupLayout.setVerticalGroup(sequentialLayout);
            
            // Get the parameters
            ArrayList<ExtensionParameter> extensionParameters = 
                    extensionDetails.get(extensionLoop).getExtensionParameters();
            
            int numParameters = extensionParameters.size();
            
            ArrayList <SettingsControl> settingsControl = new ArrayList<SettingsControl>();
            
            for(int parameterLoop = 0; parameterLoop < numParameters; parameterLoop ++)
            {
                // Create controls
                JLabel parameterLabel = new JLabel();
                parameterLabel.setText(extensionParameters.get(parameterLoop).getParameterName());
                
                JTextField parameterTextField = new JTextField();
                parameterTextField.setText(extensionParameters.get(parameterLoop).getParameterValue());
                
                // Add this to our utility array
                SettingsControl newSettingsControl = new SettingsControl();
                newSettingsControl.setSetting(extensionParameters.get(parameterLoop).getParameterName());
                newSettingsControl.setTextField(parameterTextField);
                
                settingsControl.add(newSettingsControl);
                
                // Add controls to vertical and horizontal layouts
                parallelLayout.add(parameterLabel);
                sequentialLayout.add(parameterLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE);
                parallelLayout.add(parameterTextField);
                sequentialLayout.add(parameterTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE);
            }
            
            // Add to the extension settings array
            ExtensionSettingsControls newExtensionSettingsControl = new ExtensionSettingsControls();
            newExtensionSettingsControl.setExtensionDetails(extensionDetails.get(extensionLoop));
            newExtensionSettingsControl.setSettingsControl(settingsControl);
            
            extensionSettingsControls.add(newExtensionSettingsControl);
            
            // Add the panel
            jExtensionsTabbedPane.addTab(
                    extensionDetails.get(extensionLoop).getExtensionName(), 
                    extensionPanel);
        }
    }

    public ArrayList<ExtensionSettingsControls> getExtensionSettingsControls() {
        return extensionSettingsControls;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jExtensionsTabbedPane = new javax.swing.JTabbedPane();
        jButtonSaveSettings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonSaveSettings.setText("Save Settings");
        jButtonSaveSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveSettingsActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jExtensionsTabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(jButtonSaveSettings)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jExtensionsTabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonSaveSettings)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveSettingsActionPerformed
        int numSettingTabs = extensionSettingsControls.size();
        
        try 
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("settings");
            doc.appendChild(rootElement);

            // Perpare content elements for XML
            for(int settingLoop = 0; settingLoop < numSettingTabs; settingLoop ++)
            {
                ArrayList<SettingsControl> currentSettingsControls = extensionSettingsControls.get(settingLoop).getSettingsControl();
                int numParameters = currentSettingsControls.size();

                // Child elements
                for(int parameterLoop = 0; parameterLoop < numParameters; parameterLoop ++)
                {
                    Element extensionNameNode = doc.createElement(EXTENSION_NODE);
                    extensionNameNode.appendChild(doc.createTextNode(extensionSettingsControls.get(settingLoop).getExtensionDetails().getExtensionName()));
                    rootElement.appendChild(extensionNameNode);
                    
                    Element parameterNode = doc.createElement(PARAM_NAME_NODE);
                    parameterNode.appendChild(doc.createTextNode(currentSettingsControls.get(parameterLoop).getSetting()));
                    rootElement.appendChild(parameterNode);

                    Element parameterValueNode = doc.createElement(PARAM_VALUE_NODE);
                    parameterValueNode.appendChild(doc.createTextNode(currentSettingsControls.get(parameterLoop).getTextField().getText()));
                    rootElement.appendChild(parameterValueNode);
                }
            }

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathToSettingsFile));
            
            // Save
            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(AnonymousDataCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSaveSettingsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSaveSettings;
    private javax.swing.JTabbedPane jExtensionsTabbedPane;
    // End of variables declaration//GEN-END:variables
}
