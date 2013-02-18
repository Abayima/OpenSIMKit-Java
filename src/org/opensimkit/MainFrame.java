/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit;

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
        if(!dataCollectionInterfaceConstructed) {
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
        }
        
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
        
        if(!connectedInterfaceConstructed) {
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
        }
        
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
        
        if(!disconnectedInterfaceConstructed) {
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
        }
        
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
        jButtonFaceBook = new javax.swing.JButton();
        jButtonGitHub = new javax.swing.JButton();
        jButtonTwitter = new javax.swing.JButton();
        jButtonRSS = new javax.swing.JButton();
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

        SIMKitLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/SIMKitLogo.png"))); // NOI18N

        jPanelBottomLeft.setBackground(new java.awt.Color(255, 255, 255));

        jLabelAboutOSK.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabelAboutOSK.setText("<html><div style=\"width:100%;\">SIMKIT is a free and open source software designed to turn the world's feature phones into low cost e-readers</div></html>");

        jButtonFaceBook.setBackground(new java.awt.Color(26, 26, 26));
        jButtonFaceBook.setForeground(new java.awt.Color(26, 26, 26));
        jButtonFaceBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/Facebook.png"))); // NOI18N
        jButtonFaceBook.setBorder(null);

        jButtonGitHub.setBackground(new java.awt.Color(26, 26, 26));
        jButtonGitHub.setForeground(new java.awt.Color(26, 26, 26));
        jButtonGitHub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/GitHub.png"))); // NOI18N
        jButtonGitHub.setBorder(null);

        jButtonTwitter.setBackground(new java.awt.Color(26, 26, 26));
        jButtonTwitter.setForeground(new java.awt.Color(26, 26, 26));
        jButtonTwitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/Twitter.png"))); // NOI18N
        jButtonTwitter.setBorder(null);

        jButtonRSS.setBackground(new java.awt.Color(26, 26, 26));
        jButtonRSS.setForeground(new java.awt.Color(26, 26, 26));
        jButtonRSS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/opensimkit/resources/RSS.png"))); // NOI18N
        jButtonRSS.setBorder(null);

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
                        .add(jButtonGitHub)
                        .add(18, 18, 18)
                        .add(jButtonFaceBook)
                        .add(18, 18, 18)
                        .add(jButtonTwitter)
                        .add(18, 18, 18)
                        .add(jButtonRSS)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelBottomLeftLayout.setVerticalGroup(
            jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelBottomLeftLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelAboutOSK, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanelBottomLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonFaceBook)
                    .add(jButtonGitHub)
                    .add(jButtonTwitter)
                    .add(jButtonRSS))
                .addContainerGap(388, Short.MAX_VALUE))
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
            .add(0, 506, Short.MAX_VALUE)
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
                .addContainerGap(7, Short.MAX_VALUE)
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SIMKitLogo;
    private javax.swing.JButton jButtonFaceBook;
    private javax.swing.JButton jButtonGitHub;
    private javax.swing.JButton jButtonRSS;
    private javax.swing.JButton jButtonTwitter;
    private javax.swing.JButton jButtonTwitter1;
    private javax.swing.JLabel jLabelAbayimaLogo;
    private javax.swing.JLabel jLabelAboutOSK;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemSettings;
    private javax.swing.JPanel jPanelBottomLeft;
    private javax.swing.JPanel jPanelBottomRight;
    // End of variables declaration//GEN-END:variables
}
