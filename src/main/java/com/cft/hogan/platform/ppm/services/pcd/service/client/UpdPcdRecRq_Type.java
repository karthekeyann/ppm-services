/**
 * UpdPcdRecRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class UpdPcdRecRq_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry pcdEntry;

    public UpdPcdRecRq_Type() {
    }

    public UpdPcdRecRq_Type(
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo,
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry pcdEntry) {
           this.cdmfKeyInfo = cdmfKeyInfo;
           this.pcdEntry = pcdEntry;
    }


    /**
     * Gets the cdmfKeyInfo value for this UpdPcdRecRq_Type.
     * 
     * @return cdmfKeyInfo
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type getCdmfKeyInfo() {
        return cdmfKeyInfo;
    }


    /**
     * Sets the cdmfKeyInfo value for this UpdPcdRecRq_Type.
     * 
     * @param cdmfKeyInfo
     */
    public void setCdmfKeyInfo(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo) {
        this.cdmfKeyInfo = cdmfKeyInfo;
    }


    /**
     * Gets the pcdEntry value for this UpdPcdRecRq_Type.
     * 
     * @return pcdEntry
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry getPcdEntry() {
        return pcdEntry;
    }


    /**
     * Sets the pcdEntry value for this UpdPcdRecRq_Type.
     * 
     * @param pcdEntry
     */
    public void setPcdEntry(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry pcdEntry) {
        this.pcdEntry = pcdEntry;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdPcdRecRq_Type)) return false;
        UpdPcdRecRq_Type other = (UpdPcdRecRq_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
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
        new org.apache.axis.description.TypeDesc(UpdPcdRecRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "UpdPcdRecRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfKeyInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://MessageView/", "CdmfKeyInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfKeyInfo_Type"));
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
