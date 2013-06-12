/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opensimkit.utilities.DeviceConnection;
import org.opensimkit.utilities.Messages;

/**
 *
 * @author ahmedmaawy
 */
public final class MainFrame extends javax.swing.JFrame {
    
    private PermissionsPanel permissionsPanel = new PermissionsPanel();
    private ConnectedPanel connectedPanel = new ConnectedPanel();
    private DisconnectedPanel disconnectedPanel = new DisconnectedPanel();
    
    private boolean dataCollectionInterfaceConstructed = false;
    private boolean connectedInterfaceConstructed = false;
    private boolean disconnectedInterfaceConstructed = false;
    
    private final String openSIMKitUrl = "http://opensimkit.com/";
    private final String gitHubUrl = "https://github.com/Abayima/OpenSIMKit-Java";
    private final String twitterUrl = "https://twitter.com/opensimkit";
    
    /**
     * Creates new form MainJFrame
     */
    
    public MainFrame() {
        initComponents();
        
        if(OpenSIMKit.anonymousDataCollection.isSettingsFileExists()) {
            setDisconnectedInterface();
        }
        else {
            setAnonymousDataCollectionInterface();
        }
    }
    
    /**
     * Anonymous data collection panel
     */
    
    public void setAnonymousDataCollectionInterface()
    {
        
        jPanelBottomRight.removeAll();

        org.jdesktop.layout.GroupLayout jPanelBottomRightLayout = new org.jdesktop.layout.GroupLayout(jPanelBottomRight);
        jPanelBottomRight.setLayout(jPanelBottomRightLayout);

        jPanelBottomRightLayout.setHorizontalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomRightLayout.createSequentialGroup()
                .add(permissionsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanelBottomRightLayout.setVerticalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomRightLayout.createSequentialGroup()
                .add(jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(permissionsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, Short.MAX_VALUE))
        );

        dataCollectionInterfaceConstructed = true;
        
        permissionsPanel.setVisible(true);
        disconnectedPanel.setVisible(false);
        connectedPanel.setVisible(false);
    }
    
    /**
     * Change the interface to a connected one
     */
    
    public void setConnectedInterface()
    {
        // Contruct interface

        jPanelBottomRight.removeAll();

        org.jdesktop.layout.GroupLayout jPanelBottomRightLayout = new org.jdesktop.layout.GroupLayout(jPanelBottomRight);
        jPanelBottomRight.setLayout(jPanelBottomRightLayout);

        jPanelBottomRightLayout.setHorizontalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomRightLayout.createSequentialGroup()
                .add(connectedPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanelBottomRightLayout.setVerticalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomRightLayout.createSequentialGroup()
                .add(jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(connectedPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, Short.MAX_VALUE))
        );

        connectedInterfaceConstructed = true;
        
        // Toggle interface
        
        permissionsPanel.setVisible(false);
        disconnectedPanel.setVisible(false);
        connectedPanel.setVisible(true);
        
        // Read all messages from SIM Card
        
        if(OpenSIMKit.deviceConnection.isDeviceConnected())
        {
            if(OpenSIMKit.deviceConnection.getConnectionMode() == DeviceConnection.deviceConnectionMode.driver)
            {
                // Developed driver
                String allMessages = OpenSIMKit.deviceConnection.getCurrentDriver().getAllMessages();
                Messages messages = new Messages(allMessages, OpenSIMKit.deviceConnection.getCurrentDriver().getDelimiter());
                connectedPanel.getMessagesPanel().setItems(messages.getMessages());
            }
            else if (OpenSIMKit.deviceConnection.getConnectionMode() == DeviceConnection.deviceConnectionMode.serial_port)
            {
                // Inbuilt OSK Driver
                String allMessages = OpenSIMKit.serialPorts.getAllMessages();
                Messages messages = new Messages(allMessages);
                connectedPanel.getMessagesPanel().setItems(messages.getMessages());
            }
        }
    }
    
    /**
     * Change the interface to a disconnected one
     */
    
    public void setDisconnectedInterface()
    {
        
        // Construct interface
            
        jPanelBottomRight.removeAll();

        org.jdesktop.layout.GroupLayout jPanelBottomRightLayout = new org.jdesktop.layout.GroupLayout(jPanelBottomRight);
        jPanelBottomRight.setLayout(jPanelBottomRightLayout);

        jPanelBottomRightLayout.setHorizontalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomRightLayout.createSequentialGroup()
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(disconnectedPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 709, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanelBottomRightLayout.setVerticalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomRightLayout.createSequentialGroup()
                .add(jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(disconnectedPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 512, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, Short.MAX_VALUE))
        );

        disconnectedInterfaceConstructed = true;
        
        // Toggle interface
        
        permissionsPanel.setVisible(false);
        connectedPanel.setVisible(false);
        disconnectedPanel.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonTwitter1 = new javax.swing.JButton();
        jPanelTop = new javax.swing.JPanel();
        SIMKitLogo1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabelAbayimaLogo1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanelBottomLeft = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        jPanel3 = new javax.swing.JPanel();
        jLabelAboutOSK = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonSIMKitConnect = new javax.swing.JButton();
        jLabelConnectWithUs = new javax.swing.JLabel();
        jButtonGitHubConnect = new javax.swing.JButton();
        jButtonTwitterConnect = new javax.swing.JButton();
        jSeparatorSocialMediaBottom = new javax.swing.JSeparator();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jPanelBottomRight = new javax.swing.JPanel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemSettings = new javax.swing.JMenuItem();

        jButtonTwitter1.setBackground(new java.awt.Color(26, 26, 26));
        jButtonTwitter1.setForeground(new java.awt.Color(26, 26, 26));
        jButtonTwitter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/GitHub.png"))); // NOI18N
        jButtonTwitter1.setBorder(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1000, 800));

        jPanelTop.setBackground(new java.awt.Color(246, 246, 246));
        jPanelTop.setMaximumSize(new java.awt.Dimension(32767, 100));
        jPanelTop.setMinimumSize(new java.awt.Dimension(652, 100));
        jPanelTop.setName("JPanelNorth"); // NOI18N
        jPanelTop.setPreferredSize(new java.awt.Dimension(570, 100));
        jPanelTop.setLayout(new javax.swing.BoxLayout(jPanelTop, javax.swing.BoxLayout.LINE_AXIS));

        SIMKitLogo1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SIMKitLogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/SIMKitLogo.png"))); // NOI18N
        SIMKitLogo1.setMaximumSize(new java.awt.Dimension(642, 100));
        SIMKitLogo1.setMinimumSize(new java.awt.Dimension(642, 100));
        SIMKitLogo1.setPreferredSize(new java.awt.Dimension(642, 100));
        jPanelTop.add(SIMKitLogo1);
        jPanelTop.add(filler1);

        jLabelAbayimaLogo1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAbayimaLogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/AbayimaLogo.png"))); // NOI18N
        jPanelTop.add(jLabelAbayimaLogo1);

        getContentPane().add(jPanelTop, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanelBottomLeft.setBackground(new java.awt.Color(246, 246, 246));
        jPanelBottomLeft.setMaximumSize(new java.awt.Dimension(250, 32767));
        jPanelBottomLeft.setMinimumSize(new java.awt.Dimension(250, 0));
        jPanelBottomLeft.setPreferredSize(new java.awt.Dimension(250, 620));
        jPanelBottomLeft.setLayout(new javax.swing.BoxLayout(jPanelBottomLeft, javax.swing.BoxLayout.PAGE_AXIS));
        jPanelBottomLeft.add(filler3);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabelAboutOSK.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabelAboutOSK.setText("<html><div style=\"width:100%;\">SIMKIT is a free and open source software designed to turn the world's feature phones into low cost e-readers</div></html>");
        jPanel3.add(jLabelAboutOSK);

        jPanelBottomLeft.add(jPanel3);

        jButtonSIMKitConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/SIMKitConnect.png"))); // NOI18N
        jButtonSIMKitConnect.setBorder(null);
        jButtonSIMKitConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSIMKitConnectActionPerformed(evt);
            }
        });

        jLabelConnectWithUs.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        jLabelConnectWithUs.setText("Connect with the OpenSIMKit Project");

        jButtonGitHubConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/GitHubConnect.png"))); // NOI18N
        jButtonGitHubConnect.setBorder(null);
        jButtonGitHubConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGitHubConnectActionPerformed(evt);
            }
        });

        jButtonTwitterConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/TwitterConnect.png"))); // NOI18N
        jButtonTwitterConnect.setBorder(null);
        jButtonTwitterConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTwitterConnectActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelConnectWithUs)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jButtonSIMKitConnect)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonGitHubConnect)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonTwitterConnect)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelConnectWithUs)
                .add(8, 8, 8)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonSIMKitConnect)
                    .add(jButtonGitHubConnect)
                    .add(jButtonTwitterConnect))
                .addContainerGap())
        );

        jPanelBottomLeft.add(jPanel2);
        jPanelBottomLeft.add(jSeparatorSocialMediaBottom);
        jPanelBottomLeft.add(filler2);

        jPanel1.add(jPanelBottomLeft);

        jPanelBottomRight.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBottomRight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.jdesktop.layout.GroupLayout jPanelBottomRightLayout = new org.jdesktop.layout.GroupLayout(jPanelBottomRight);
        jPanelBottomRight.setLayout(jPanelBottomRightLayout);
        jPanelBottomRightLayout.setHorizontalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jPanelBottomRightLayout.setVerticalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 176, Short.MAX_VALUE)
        );

        jPanel1.add(jPanelBottomRight);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenuFile.setText("File");

        jMenuItemSettings.setText("Settings");
        jMenuItemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSettingsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSettings);

        jMenuBar.add(jMenuFile);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSettingsActionPerformed
        // TODO add your handling code here:
        SettingsFrame settingsFrame = new SettingsFrame();
        settingsFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItemSettingsActionPerformed

    private void jButtonSIMKitConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSIMKitConnectActionPerformed
        // TODO add your handling code here:
        try {            
            URI uri = new URI(this.openSIMKitUrl);
            Desktop dt = Desktop.getDesktop();
            dt.browse(uri);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSIMKitConnectActionPerformed

    private void jButtonGitHubConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGitHubConnectActionPerformed
        // TODO add your handling code here:
        try {            
            URI uri = new URI(this.gitHubUrl);
            Desktop dt = Desktop.getDesktop();
            dt.browse(uri);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonGitHubConnectActionPerformed

    private void jButtonTwitterConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTwitterConnectActionPerformed
        // TODO add your handling code here:
        try {            
            URI uri = new URI(this.twitterUrl);
            Desktop dt = Desktop.getDesktop();
            dt.browse(uri);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonTwitterConnectActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SIMKitLogo1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JButton jButtonGitHubConnect;
    private javax.swing.JButton jButtonSIMKitConnect;
    private javax.swing.JButton jButtonTwitter1;
    private javax.swing.JButton jButtonTwitterConnect;
    private javax.swing.JLabel jLabelAbayimaLogo1;
    private javax.swing.JLabel jLabelAboutOSK;
    private javax.swing.JLabel jLabelConnectWithUs;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemSettings;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBottomLeft;
    private javax.swing.JPanel jPanelBottomRight;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JSeparator jSeparatorSocialMediaBottom;
    // End of variables declaration//GEN-END:variables
}
