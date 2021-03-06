/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensimkit.utilities;

import org.opensimkit.drivers.DriverInterface;

/**
 *
 * @author ahmedmaawy
 */
public class DriverDetails {
    private String manufacturer;
    private String model;
    private String revision;
    private String driverClass;
    private boolean genericConnection;
    private String delimiter;
    private DriverInterface driverInterface;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public boolean isGenericConnection() {
        return genericConnection;
    }

    public void setGenericConnection(boolean genericConnection) {
        this.genericConnection = genericConnection;
    }
    
    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public DriverInterface getDriverInterface() {
        return driverInterface;
    }

    public void setDriverInterface(DriverInterface driverInterface) {
        this.driverInterface = driverInterface;
    }
}
