/**
 * UpdPcdRecRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.api.pcd.service.client;

public class UpdPcdRecRs_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.api.pcd.service.client.Status_Type XStatus;

    private com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo;

    private com.cft.hogan.platform.ppm.api.pcd.service.client.PcdEntry pcdEntry;

    public UpdPcdRecRs_Type() {
    }

    public UpdPcdRecRs_Type(
           com.cft.hogan.platform.ppm.api.pcd.service.client.Status_Type XStatus,
           com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo,
           com.cft.hogan.platform.ppm.api.pcd.service.client.PcdEntry pcdEntry) {
           this.XStatus = XStatus;
           this.cdmfKeyInfo = cdmfKeyInfo;
           this.pcdEntry = pcdEntry;
    }


    /**
     * Gets the XStatus value for this UpdPcdRecRs_Type.
     * 
     * @return XStatus
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.Status_Type getXStatus() {
        return XStatus;
    }


    /**
     * Sets the XStatus value for this UpdPcdRecRs_Type.
     * 
     * @param XStatus
     */
    public void setXStatus(com.cft.hogan.platform.ppm.api.pcd.service.client.Status_Type XStatus) {
        this.XStatus = XStatus;
    }


    /**
     * Gets the cdmfKeyInfo value for this UpdPcdRecRs_Type.
     * 
     * @return cdmfKeyInfo
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfKeyInfo_Type getCdmfKeyInfo() {
        return cdmfKeyInfo;
    }


    /**
     * Sets the cdmfKeyInfo value for this UpdPcdRecRs_Type.
     * 
     * @param cdmfKeyInfo
     */
    public void setCdmfKeyInfo(com.cft.hogan.platform.ppm.api.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo) {
        this.cdmfKeyInfo = cdmfKeyInfo;
    }


    /**
     * Gets the pcdEntry value for this UpdPcdRecRs_Type.
     * 
     * @return pcdEntry
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.PcdEntry getPcdEntry() {
        return pcdEntry;
    }


    /**
     * Sets the pcdEntry value for this UpdPcdRecRs_Type.
     * 
     * @param pcdEntry
     */
    public void setPcdEntry(com.cft.hogan.platform.ppm.api.pcd.service.client.PcdEntry pcdEntry) {
        this.pcdEntry = pcdEntry;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdPcdRecRs_Type)) return false;
        UpdPcdRecRs_Type other = (UpdPcdRecRs_Type) obj;
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
            ((this.pcdEntry==null && other.getPcdEntry()==null) || 
             (this.pcdEntry!=null &&
              this.pcdEntry.equals(other.getPcdEntry())));
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
        if (getPcdEntry() != null) {
            _hashCode += getPcdEntry().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdPcdRecRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "UpdPcdRecRs_Type"));
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
        elemField.setFieldName("pcdEntry");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdEntry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdEntry"));
        elemField.setMinOccurs(0);
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
