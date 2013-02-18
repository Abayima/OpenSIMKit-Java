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
        
        if(OpenSIMKit.serialPorts.isConnected())
        {
            String allMessages = OpenSIMKit.serialPorts.getAllMessages();
            Messages messages = new Messages(allMessages);
            connectedPanel.getMessagesPanel().setItems(messages.getMessages());
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
        SIMKitLogo = new javax.swing.JLabel();
        jPanelBottomLeft = new javax.swing.JPanel();
        jLabelAboutOSK = new javax.swing.JLabel();
        jSeparatorSocialMediaTop = new javax.swing.JSeparator();
        jSeparatorSocialMediaBottom = new javax.swing.JSeparator();
        jLabelConnectWithUs = new javax.swing.JLabel();
        jButtonSIMKitConnect = new javax.swing.JButton();
        jButtonGitHubConnect = new javax.swing.JButton();
        jButtonTwitterConnect = new javax.swing.JButton();
        jPanelBottomRight = new javax.swing.JPanel();
        jLabelAbayimaLogo = new javax.swing.JLabel();
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

        SIMKitLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/SIMKitLogo.png"))); // NOI18N

        jPanelBottomLeft.setBackground(new java.awt.Color(255, 255, 255));

        jLabelAboutOSK.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabelAboutOSK.setText("<html><div style=\"width:100%;\">SIMKIT is a free and open source software designed to turn the world's feature phones into low cost e-readers</div></html>");

        jLabelConnectWithUs.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        jLabelConnectWithUs.setText("Connect with the OpenSIMKit Project");

        jButtonSIMKitConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/SIMKitConnect.png"))); // NOI18N
        jButtonSIMKitConnect.setBorder(null);
        jButtonSIMKitConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSIMKitConnectActionPerformed(evt);
            }
        });

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

        org.jdesktop.layout.GroupLayout jPanelBottomLeftLayout = new org.jdesktop.layout.GroupLayout(jPanelBottomLeft);
        jPanelBottomLeft.setLayout(jPanelBottomLeftLayout);
        jPanelBottomLeftLayout.setHorizontalGroup(
            jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomLeftLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelBottomLeftLayout.createSequentialGroup()
                        .add(jLabelAboutOSK, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 231, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jPanelBottomLeftLayout.createSequentialGroup()
                        .add(jSeparatorSocialMediaTop)
                        .addContainerGap())))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelBottomLeftLayout.createSequentialGroup()
                .addContainerGap()
                .add(jSeparatorSocialMediaBottom)
                .addContainerGap())
            .add(jPanelBottomLeftLayout.createSequentialGroup()
                .add(26, 26, 26)
                .add(jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelConnectWithUs)
                    .add(jPanelBottomLeftLayout.createSequentialGroup()
                        .add(jButtonSIMKitConnect)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonGitHubConnect)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonTwitterConnect)))
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanelBottomLeftLayout.setVerticalGroup(
            jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomLeftLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelAboutOSK, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(49, 49, 49)
                .add(jSeparatorSocialMediaTop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelConnectWithUs)
                .add(8, 8, 8)
                .add(jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonSIMKitConnect)
                    .add(jButtonGitHubConnect)
                    .add(jButtonTwitterConnect))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparatorSocialMediaBottom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelBottomRight.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBottomRight.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));

        org.jdesktop.layout.GroupLayout jPanelBottomRightLayout = new org.jdesktop.layout.GroupLayout(jPanelBottomRight);
        jPanelBottomRight.setLayout(jPanelBottomRightLayout);
        jPanelBottomRightLayout.setHorizontalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanelBottomRightLayout.setVerticalGroup(
            jPanelBottomRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 560, Short.MAX_VALUE)
        );

        jLabelAbayimaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/AbayimaLogo.png"))); // NOI18N

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

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanelBottomLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanelBottomRight, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(SIMKitLogo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 39, Short.MAX_VALUE)
                        .add(jLabelAbayimaLogo)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(SIMKitLogo)
                    .add(jLabelAbayimaLogo))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanelBottomLeft, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanelBottomRight, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

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
    private javax.swing.JLabel SIMKitLogo;
    private javax.swing.JButton jButtonGitHubConnect;
    private javax.swing.JButton jButtonSIMKitConnect;
    private javax.swing.JButton jButtonTwitter1;
    private javax.swing.JButton jButtonTwitterConnect;
    private javax.swing.JLabel jLabelAbayimaLogo;
    private javax.swing.JLabel jLabelAboutOSK;
    private javax.swing.JLabel jLabelConnectWithUs;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemSettings;
    private javax.swing.JPanel jPanelBottomLeft;
    private javax.swing.JPanel jPanelBottomRight;
    private javax.swing.JSeparator jSeparatorSocialMediaBottom;
    private javax.swing.JSeparator jSeparatorSocialMediaTop;
    // End of variables declaration//GEN-END:variables
}
