/**
 * PartyRef_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.api.pcd.service.client;

public class PartyRef_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.api.pcd.service.client.LoginIdent_Type loginIdent;

    public PartyRef_Type() {
    }

    public PartyRef_Type(
           com.cft.hogan.platform.ppm.api.pcd.service.client.LoginIdent_Type loginIdent) {
           this.loginIdent = loginIdent;
    }


    /**
     * Gets the loginIdent value for this PartyRef_Type.
     * 
     * @return loginIdent
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.LoginIdent_Type getLoginIdent() {
        return loginIdent;
    }


    /**
     * Sets the loginIdent value for this PartyRef_Type.
     * 
     * @param loginIdent
     */
    public void setLoginIdent(com.cft.hogan.platform.ppm.api.pcd.service.client.LoginIdent_Type loginIdent) {
        this.loginIdent = loginIdent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PartyRef_Type)) return false;
        PartyRef_Type other = (PartyRef_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loginIdent==null && other.getLoginIdent()==null) || 
             (this.loginIdent!=null &&
              this.loginIdent.equals(other.getLoginIdent())));
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
        if (getLoginIdent() != null) {
            _hashCode += getLoginIdent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PartyRef_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PartyRef_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginIdent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LoginIdent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "LoginIdent_Type"));
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
