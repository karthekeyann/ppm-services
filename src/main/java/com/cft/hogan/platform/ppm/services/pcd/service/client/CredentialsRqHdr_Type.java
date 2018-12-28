/**
 * CredentialsRqHdr_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class CredentialsRqHdr_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.services.pcd.service.client.PartyRef_Type partyRef;

    public CredentialsRqHdr_Type() {
    }

    public CredentialsRqHdr_Type(
           com.cft.hogan.platform.ppm.services.pcd.service.client.PartyRef_Type partyRef) {
           this.partyRef = partyRef;
    }


    /**
     * Gets the partyRef value for this CredentialsRqHdr_Type.
     * 
     * @return partyRef
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PartyRef_Type getPartyRef() {
        return partyRef;
    }


    /**
     * Sets the partyRef value for this CredentialsRqHdr_Type.
     * 
     * @param partyRef
     */
    public void setPartyRef(com.cft.hogan.platform.ppm.services.pcd.service.client.PartyRef_Type partyRef) {
        this.partyRef = partyRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialsRqHdr_Type)) return false;
        CredentialsRqHdr_Type other = (CredentialsRqHdr_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.partyRef==null && other.getPartyRef()==null) || 
             (this.partyRef!=null &&
              this.partyRef.equals(other.getPartyRef())));
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
        if (getPartyRef() != null) {
            _hashCode += getPartyRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialsRqHdr_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CredentialsRqHdr_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partyRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PartyRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PartyRef_Type"));
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
