/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import org.opensimkit.extensions.ExtensionInterface;

/**
 *
 * @author ahmedmaawy
 */
public class ExtensionDetails {
    private String extensionClass;
    private ExtensionInterface extensionInterface;

    public String getExtensionClass() {
        return extensionClass;
    }

    public void setExtensionClass(String extensionClass) {
        this.extensionClass = extensionClass;
    }

    public ExtensionInterface getExtensionInterface() {
        return extensionInterface;
    }

    public void setExtensionInterface(ExtensionInterface extensionInterface) {
        this.extensionInterface = extensionInterface;
    }
}
