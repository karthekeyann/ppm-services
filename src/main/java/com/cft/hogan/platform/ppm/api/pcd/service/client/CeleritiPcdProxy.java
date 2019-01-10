package com.cft.hogan.platform.ppm.api.pcd.service.client;

public class CeleritiPcdProxy implements com.cft.hogan.platform.ppm.api.pcd.service.client.CeleritiPcd_PortType {
  private String _endpoint = null;
  private com.cft.hogan.platform.ppm.api.pcd.service.client.CeleritiPcd_PortType celeritiPcd_PortType = null;
  
  public CeleritiPcdProxy() {
    _initCeleritiPcdProxy();
  }
  
  public CeleritiPcdProxy(String endpoint) {
    _endpoint = endpoint;
    _initCeleritiPcdProxy();
  }
  
  private void _initCeleritiPcdProxy() {
    try {
      celeritiPcd_PortType = (new com.cft.hogan.platform.ppm.api.pcd.service.client.CeleritiPcd_ServiceLocator()).getCeleritiPcd();
      if (celeritiPcd_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)celeritiPcd_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)celeritiPcd_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (celeritiPcd_PortType != null)
      ((javax.xml.rpc.Stub)celeritiPcd_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cft.hogan.platform.ppm.api.pcd.service.client.CeleritiPcd_PortType getCeleritiPcd_PortType() {
    if (celeritiPcd_PortType == null)
      _initCeleritiPcdProxy();
    return celeritiPcd_PortType;
  }
  
  public com.cft.hogan.platform.ppm.api.pcd.service.client.PcdXmlRs_Type processPcd(com.cft.hogan.platform.ppm.api.pcd.service.client.PcdXmlRq_Type pcdXmlRq) throws java.rmi.RemoteException{
    if (celeritiPcd_PortType == null)
      _initCeleritiPcdProxy();
    return celeritiPcd_PortType.processPcd(pcdXmlRq);
  }
  
  public com.cft.hogan.platform.ppm.api.pcd.service.client.UpdPcdRecRs_Type[] updatePcd(com.cft.hogan.platform.ppm.api.pcd.service.client.UpdatePcdRq_Type updatePcdRq) throws java.rmi.RemoteException{
    if (celeritiPcd_PortType == null)
      _initCeleritiPcdProxy();
    return celeritiPcd_PortType.updatePcd(updatePcdRq);
  }
  
  public com.cft.hogan.platform.ppm.api.pcd.service.client.PcdCacheRs_Type pcdCache(com.cft.hogan.platform.ppm.api.pcd.service.client.PcdCacheRq_Type pcdCacheRq) throws java.rmi.RemoteException{
    if (celeritiPcd_PortType == null)
      _initCeleritiPcdProxy();
    return celeritiPcd_PortType.pcdCache(pcdCacheRq);
  }
  
  
}