/**
 * UpdatePcdRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class UpdatePcdRq_Type  implements java.io.Serializable {
    private java.lang.String actionType;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type[] updPcdRecRq;

    public UpdatePcdRq_Type() {
    }

    public UpdatePcdRq_Type(
           java.lang.String actionType,
           com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type[] updPcdRecRq) {
           this.actionType = actionType;
           this.updPcdRecRq = updPcdRecRq;
    }


    /**
     * Gets the actionType value for this UpdatePcdRq_Type.
     * 
     * @return actionType
     */
    public java.lang.String getActionType() {
        return actionType;
    }


    /**
     * Sets the actionType value for this UpdatePcdRq_Type.
     * 
     * @param actionType
     */
    public void setActionType(java.lang.String actionType) {
        this.actionType = actionType;
    }


    /**
     * Gets the updPcdRecRq value for this UpdatePcdRq_Type.
     * 
     * @return updPcdRecRq
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type[] getUpdPcdRecRq() {
        return updPcdRecRq;
    }


    /**
     * Sets the updPcdRecRq value for this UpdatePcdRq_Type.
     * 
     * @param updPcdRecRq
     */
    public void setUpdPcdRecRq(com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type[] updPcdRecRq) {
        this.updPcdRecRq = updPcdRecRq;
    }

    public com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type getUpdPcdRecRq(int i) {
        return this.updPcdRecRq[i];
    }

    public void setUpdPcdRecRq(int i, com.cft.hogan.platform.ppm.services.pcd.service.client.UpdPcdRecRq_Type _value) {
        this.updPcdRecRq[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdatePcdRq_Type)) return false;
        UpdatePcdRq_Type other = (UpdatePcdRq_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionType==null && other.getActionType()==null) || 
             (this.actionType!=null &&
              this.actionType.equals(other.getActionType()))) &&
            ((this.updPcdRecRq==null && other.getUpdPcdRecRq()==null) || 
             (this.updPcdRecRq!=null &&
              java.util.Arrays.equals(this.updPcdRecRq, other.getUpdPcdRecRq())));
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
        if (getActionType() != null) {
            _hashCode += getActionType().hashCode();
        }
        if (getUpdPcdRecRq() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUpdPcdRecRq());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUpdPcdRecRq(), i);
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
        new org.apache.axis.description.TypeDesc(UpdatePcdRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "UpdatePcdRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ActionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updPcdRecRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://MessageView/", "UpdPcdRecRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "UpdPcdRecRq_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
