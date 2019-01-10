/**
 * Binary_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.api.pcd.service.client;

public class Binary_Type  implements java.io.Serializable {
    private java.lang.String contentType;

    private long binLength;

    private byte[] binData;

    public Binary_Type() {
    }

    public Binary_Type(
           java.lang.String contentType,
           long binLength,
           byte[] binData) {
           this.contentType = contentType;
           this.binLength = binLength;
           this.binData = binData;
    }


    /**
     * Gets the contentType value for this Binary_Type.
     * 
     * @return contentType
     */
    public java.lang.String getContentType() {
        return contentType;
    }


    /**
     * Sets the contentType value for this Binary_Type.
     * 
     * @param contentType
     */
    public void setContentType(java.lang.String contentType) {
        this.contentType = contentType;
    }


    /**
     * Gets the binLength value for this Binary_Type.
     * 
     * @return binLength
     */
    public long getBinLength() {
        return binLength;
    }


    /**
     * Sets the binLength value for this Binary_Type.
     * 
     * @param binLength
     */
    public void setBinLength(long binLength) {
        this.binLength = binLength;
    }


    /**
     * Gets the binData value for this Binary_Type.
     * 
     * @return binData
     */
    public byte[] getBinData() {
        return binData;
    }


    /**
     * Sets the binData value for this Binary_Type.
     * 
     * @param binData
     */
    public void setBinData(byte[] binData) {
        this.binData = binData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Binary_Type)) return false;
        Binary_Type other = (Binary_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contentType==null && other.getContentType()==null) || 
             (this.contentType!=null &&
              this.contentType.equals(other.getContentType()))) &&
            this.binLength == other.getBinLength() &&
            ((this.binData==null && other.getBinData()==null) || 
             (this.binData!=null &&
              java.util.Arrays.equals(this.binData, other.getBinData())));
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
        if (getContentType() != null) {
            _hashCode += getContentType().hashCode();
        }
        _hashCode += new Long(getBinLength()).hashCode();
        if (getBinData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBinData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBinData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Binary_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "Binary_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binLength");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BinLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BinData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
