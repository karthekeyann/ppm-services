/**
 * CeleritiPcd_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class CeleritiPcd_ServiceLocator extends org.apache.axis.client.Service implements com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_Service {

    public CeleritiPcd_ServiceLocator() {
    }


    public CeleritiPcd_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CeleritiPcd_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CeleritiPcd
    private java.lang.String CeleritiPcd_address = "http://20.17.189.77:3035/Celeriti/CeleritiPcd";

    public java.lang.String getCeleritiPcdAddress() {
        return CeleritiPcd_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CeleritiPcdWSDDServiceName = "CeleritiPcd";

    public java.lang.String getCeleritiPcdWSDDServiceName() {
        return CeleritiPcdWSDDServiceName;
    }

    public void setCeleritiPcdWSDDServiceName(java.lang.String name) {
        CeleritiPcdWSDDServiceName = name;
    }

    public com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_PortType getCeleritiPcd() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CeleritiPcd_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCeleritiPcd(endpoint);
    }

    public com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_PortType getCeleritiPcd(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_BindingStub _stub = new com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_BindingStub(portAddress, this);
            _stub.setPortName(getCeleritiPcdWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCeleritiPcdEndpointAddress(java.lang.String address) {
        CeleritiPcd_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_BindingStub _stub = new com.cft.hogan.platform.ppm.services.pcd.service.client.CeleritiPcd_BindingStub(new java.net.URL(CeleritiPcd_address), this);
                _stub.setPortName(getCeleritiPcdWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CeleritiPcd".equals(inputPortName)) {
            return getCeleritiPcd();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://MessageView/", "CeleritiPcd");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://MessageView/", "CeleritiPcd"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CeleritiPcd".equals(portName)) {
            setCeleritiPcdEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
