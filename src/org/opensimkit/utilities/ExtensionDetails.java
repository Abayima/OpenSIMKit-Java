/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import java.util.ArrayList;
import org.opensimkit.extensions.ExtensionInterface;
import org.opensimkit.extensions.ExtensionParameter;

/**
 *
 * @author ahmedmaawy
 */
public class ExtensionDetails {
    private String extensionClass;
    private ExtensionInterface extensionInterface;
    private String extensionName;
    private ArrayList<ExtensionParameter> extensionParameters;

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
