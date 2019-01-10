/**
 * SubjectElement_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.api.pcd.service.client;

public class SubjectElement_Type  implements java.io.Serializable {
    private java.lang.String path;

    private java.lang.String XValue;

    public SubjectElement_Type() {
    }

    public SubjectElement_Type(
           java.lang.String path,
           java.lang.String XValue) {
           this.path = path;
           this.XValue = XValue;
    }


    /**
     * Gets the path value for this SubjectElement_Type.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this SubjectElement_Type.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the XValue value for this SubjectElement_Type.
     * 
     * @return XValue
     */
    public java.lang.String getXValue() {
        return XValue;
    }


    /**
     * Sets the XValue value for this SubjectElement_Type.
     * 
     * @param XValue
     */
    public void setXValue(java.lang.String XValue) {
        this.XValue = XValue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubjectElement_Type)) return false;
        SubjectElement_Type other = (SubjectElement_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            ((this.XValue==null && other.getXValue()==null) || 
             (this.XValue!=null &&
              this.XValue.equals(other.getXValue())));
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
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        if (getXValue() != null) {
            _hashCode += getXValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubjectElement_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "SubjectElement_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("path");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "C"));
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
