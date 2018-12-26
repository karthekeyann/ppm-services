/**
 * PcdCacheRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class PcdCacheRq_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type pcdData;

    public PcdCacheRq_Type() {
    }

    public PcdCacheRq_Type(
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo,
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type pcdData) {
           this.cdmfKeyInfo = cdmfKeyInfo;
           this.pcdData = pcdData;
    }


    /**
     * Gets the cdmfKeyInfo value for this PcdCacheRq_Type.
     * 
     * @return cdmfKeyInfo
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type getCdmfKeyInfo() {
        return cdmfKeyInfo;
    }


    /**
     * Sets the cdmfKeyInfo value for this PcdCacheRq_Type.
     * 
     * @param cdmfKeyInfo
     */
    public void setCdmfKeyInfo(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo) {
        this.cdmfKeyInfo = cdmfKeyInfo;
    }


    /**
     * Gets the pcdData value for this PcdCacheRq_Type.
     * 
     * @return pcdData
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type getPcdData() {
        return pcdData;
    }


    /**
     * Sets the pcdData value for this PcdCacheRq_Type.
     * 
     * @param pcdData
     */
    public void setPcdData(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type pcdData) {
        this.pcdData = pcdData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PcdCacheRq_Type)) return false;
        PcdCacheRq_Type other = (PcdCacheRq_Type) obj;
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
            ((this.pcdData==null && other.getPcdData()==null) || 
             (this.pcdData!=null &&
              this.pcdData.equals(other.getPcdData())));
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
        if (getPcdData() != null) {
            _hashCode += getPcdData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PcdCacheRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdCacheRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfKeyInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfKeyInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfKeyInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdData_Type"));
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
