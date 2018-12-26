/**
 * PcdCacheRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class PcdCacheRs_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.services.pcd.service.client.Status_Type XStatus;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_Type pcdItemList;

    public PcdCacheRs_Type() {
    }

    public PcdCacheRs_Type(
           com.cft.hogan.platform.ppm.services.pcd.service.client.Status_Type XStatus,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo,
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_Type pcdItemList) {
           this.XStatus = XStatus;
           this.cdmfKeyInfo = cdmfKeyInfo;
           this.pcdItemList = pcdItemList;
    }


    /**
     * Gets the XStatus value for this PcdCacheRs_Type.
     * 
     * @return XStatus
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.Status_Type getXStatus() {
        return XStatus;
    }


    /**
     * Sets the XStatus value for this PcdCacheRs_Type.
     * 
     * @param XStatus
     */
    public void setXStatus(com.cft.hogan.platform.ppm.services.pcd.service.client.Status_Type XStatus) {
        this.XStatus = XStatus;
    }


    /**
     * Gets the cdmfKeyInfo value for this PcdCacheRs_Type.
     * 
     * @return cdmfKeyInfo
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type getCdmfKeyInfo() {
        return cdmfKeyInfo;
    }


    /**
     * Sets the cdmfKeyInfo value for this PcdCacheRs_Type.
     * 
     * @param cdmfKeyInfo
     */
    public void setCdmfKeyInfo(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo) {
        this.cdmfKeyInfo = cdmfKeyInfo;
    }


    /**
     * Gets the pcdItemList value for this PcdCacheRs_Type.
     * 
     * @return pcdItemList
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_Type getPcdItemList() {
        return pcdItemList;
    }


    /**
     * Sets the pcdItemList value for this PcdCacheRs_Type.
     * 
     * @param pcdItemList
     */
    public void setPcdItemList(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_Type pcdItemList) {
        this.pcdItemList = pcdItemList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PcdCacheRs_Type)) return false;
        PcdCacheRs_Type other = (PcdCacheRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.XStatus==null && other.getXStatus()==null) || 
             (this.XStatus!=null &&
              this.XStatus.equals(other.getXStatus()))) &&
            ((this.cdmfKeyInfo==null && other.getCdmfKeyInfo()==null) || 
             (this.cdmfKeyInfo!=null &&
              this.cdmfKeyInfo.equals(other.getCdmfKeyInfo()))) &&
            ((this.pcdItemList==null && other.getPcdItemList()==null) || 
             (this.pcdItemList!=null &&
              this.pcdItemList.equals(other.getPcdItemList())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getXStatus() != null) {
            _hashCode += getXStatus().hashCode();
        }
        if (getCdmfKeyInfo() != null) {
            _hashCode += getCdmfKeyInfo().hashCode();
        }
        if (getPcdItemList() != null) {
            _hashCode += getPcdItemList().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PcdCacheRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdCacheRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfKeyInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfKeyInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfKeyInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdItemList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdItemList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdItemList_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
