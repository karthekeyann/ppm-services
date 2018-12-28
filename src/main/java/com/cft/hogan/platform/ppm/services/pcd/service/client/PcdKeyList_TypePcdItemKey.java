/**
 * PcdKeyList_TypePcdItemKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class PcdKeyList_TypePcdItemKey  implements java.io.Serializable {
    private long cdmfFmtCoId;

    private java.lang.String cdmfFmtEffDt;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type cdmfRegKey;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type cdmfCdkKey;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type cdmfSimKey;

    public PcdKeyList_TypePcdItemKey() {
    }

    public PcdKeyList_TypePcdItemKey(
           long cdmfFmtCoId,
           java.lang.String cdmfFmtEffDt,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type cdmfRegKey,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type cdmfCdkKey,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type cdmfSimKey) {
           this.cdmfFmtCoId = cdmfFmtCoId;
           this.cdmfFmtEffDt = cdmfFmtEffDt;
           this.cdmfRegKey = cdmfRegKey;
           this.cdmfCdkKey = cdmfCdkKey;
           this.cdmfSimKey = cdmfSimKey;
    }


    /**
     * Gets the cdmfFmtCoId value for this PcdKeyList_TypePcdItemKey.
     * 
     * @return cdmfFmtCoId
     */
    public long getCdmfFmtCoId() {
        return cdmfFmtCoId;
    }


    /**
     * Sets the cdmfFmtCoId value for this PcdKeyList_TypePcdItemKey.
     * 
     * @param cdmfFmtCoId
     */
    public void setCdmfFmtCoId(long cdmfFmtCoId) {
        this.cdmfFmtCoId = cdmfFmtCoId;
    }


    /**
     * Gets the cdmfFmtEffDt value for this PcdKeyList_TypePcdItemKey.
     * 
     * @return cdmfFmtEffDt
     */
    public java.lang.String getCdmfFmtEffDt() {
        return cdmfFmtEffDt;
    }


    /**
     * Sets the cdmfFmtEffDt value for this PcdKeyList_TypePcdItemKey.
     * 
     * @param cdmfFmtEffDt
     */
    public void setCdmfFmtEffDt(java.lang.String cdmfFmtEffDt) {
        this.cdmfFmtEffDt = cdmfFmtEffDt;
    }


    /**
     * Gets the cdmfRegKey value for this PcdKeyList_TypePcdItemKey.
     * 
     * @return cdmfRegKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type getCdmfRegKey() {
        return cdmfRegKey;
    }


    /**
     * Sets the cdmfRegKey value for this PcdKeyList_TypePcdItemKey.
     * 
     * @param cdmfRegKey
     */
    public void setCdmfRegKey(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type cdmfRegKey) {
        this.cdmfRegKey = cdmfRegKey;
    }


    /**
     * Gets the cdmfCdkKey value for this PcdKeyList_TypePcdItemKey.
     * 
     * @return cdmfCdkKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type getCdmfCdkKey() {
        return cdmfCdkKey;
    }


    /**
     * Sets the cdmfCdkKey value for this PcdKeyList_TypePcdItemKey.
     * 
     * @param cdmfCdkKey
     */
    public void setCdmfCdkKey(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type cdmfCdkKey) {
        this.cdmfCdkKey = cdmfCdkKey;
    }


    /**
     * Gets the cdmfSimKey value for this PcdKeyList_TypePcdItemKey.
     * 
     * @return cdmfSimKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type getCdmfSimKey() {
        return cdmfSimKey;
    }


    /**
     * Sets the cdmfSimKey value for this PcdKeyList_TypePcdItemKey.
     * 
     * @param cdmfSimKey
     */
    public void setCdmfSimKey(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type cdmfSimKey) {
        this.cdmfSimKey = cdmfSimKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PcdKeyList_TypePcdItemKey)) return false;
        PcdKeyList_TypePcdItemKey other = (PcdKeyList_TypePcdItemKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.cdmfFmtCoId == other.getCdmfFmtCoId() &&
            ((this.cdmfFmtEffDt==null && other.getCdmfFmtEffDt()==null) || 
             (this.cdmfFmtEffDt!=null &&
              this.cdmfFmtEffDt.equals(other.getCdmfFmtEffDt()))) &&
            ((this.cdmfRegKey==null && other.getCdmfRegKey()==null) || 
             (this.cdmfRegKey!=null &&
              this.cdmfRegKey.equals(other.getCdmfRegKey()))) &&
            ((this.cdmfCdkKey==null && other.getCdmfCdkKey()==null) || 
             (this.cdmfCdkKey!=null &&
              this.cdmfCdkKey.equals(other.getCdmfCdkKey()))) &&
            ((this.cdmfSimKey==null && other.getCdmfSimKey()==null) || 
             (this.cdmfSimKey!=null &&
              this.cdmfSimKey.equals(other.getCdmfSimKey())));
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
        _hashCode += new Long(getCdmfFmtCoId()).hashCode();
        if (getCdmfFmtEffDt() != null) {
            _hashCode += getCdmfFmtEffDt().hashCode();
        }
        if (getCdmfRegKey() != null) {
            _hashCode += getCdmfRegKey().hashCode();
        }
        if (getCdmfCdkKey() != null) {
            _hashCode += getCdmfCdkKey().hashCode();
        }
        if (getCdmfSimKey() != null) {
            _hashCode += getCdmfSimKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PcdKeyList_TypePcdItemKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", ">PcdKeyList_Type>PcdItemKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfFmtCoId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfFmtCoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "Long_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfFmtEffDt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfFmtEffDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "Date_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfRegKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfRegKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfRegKey_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfCdkKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfCdkKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfCdkKey_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfSimKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfSimKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfSimKey_Type"));
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
