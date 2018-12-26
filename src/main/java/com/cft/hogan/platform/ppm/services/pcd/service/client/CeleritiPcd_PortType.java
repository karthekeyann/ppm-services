/**
 * CeleritiPcd_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public interface CeleritiPcd_PortType extends java.rmi.Remote {
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRs_Type processPcd(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdXmlRq_Type pcdXmlRq) throws java.rmi.RemoteException;
    public com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRs_Type[] updatePcd(com.cft.hogan.platform.ppm.services.pcd.service.client.UpdatePcdRq_Type updatePcdRq) throws java.rmi.RemoteException;
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdCacheRs_Type pcdCache(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdCacheRq_Type pcdCacheRq) throws java.rmi.RemoteException;
}
