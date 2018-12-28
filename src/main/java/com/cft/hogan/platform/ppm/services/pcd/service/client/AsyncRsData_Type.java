/**
 * AsyncRsData_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class AsyncRsData_Type  implements java.io.Serializable {
    private java.lang.String availDt;

    private java.lang.String expDt;

    public AsyncRsData_Type() {
    }

    public AsyncRsData_Type(
           java.lang.String availDt,
           java.lang.String expDt) {
           this.availDt = availDt;
           this.expDt = expDt;
    }


    /**
     * Gets the availDt value for this AsyncRsData_Type.
     * 
     * @return availDt
     */
    public java.lang.String getAvailDt() {
        return availDt;
    }


    /**
     * Sets the availDt value for this AsyncRsData_Type.
     * 
     * @param availDt
     */
    public void setAvailDt(java.lang.String availDt) {
        this.availDt = availDt;
    }


    /**
     * Gets the expDt value for this AsyncRsData_Type.
     * 
     * @return expDt
     */
    public java.lang.String getExpDt() {
        return expDt;
    }


    /**
     * Sets the expDt value for this AsyncRsData_Type.
     * 
     * @param expDt
     */
    public void setExpDt(java.lang.String expDt) {
        this.expDt = expDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AsyncRsData_Type)) return false;
        AsyncRsData_Type other = (AsyncRsData_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.availDt==null && other.getAvailDt()==null) || 
             (this.availDt!=null &&
              this.availDt.equals(other.getAvailDt()))) &&
            ((this.expDt==null && other.getExpDt()==null) || 
             (this.expDt!=null &&
              this.expDt.equals(other.getExpDt())));
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
        if (getAvailDt() != null) {
            _hashCode += getAvailDt().hashCode();
        }
        if (getExpDt() != null) {
            _hashCode += getExpDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AsyncRsData_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "AsyncRsData_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availDt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AvailDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExpDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
