/**
 * Status_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.api.pcd.service.client;

public class Status_Type  implements java.io.Serializable {
    private long statusCode;

    private java.lang.String serverStatusCode;

    private java.lang.String severity;

    private java.lang.String statusDesc;

    private com.cft.hogan.platform.ppm.api.pcd.service.client.SubjectElement_Type[] subjectElement;

    private com.cft.hogan.platform.ppm.api.pcd.service.client.AdditionalStatus_Type[] additionalStatus;

    private com.cft.hogan.platform.ppm.api.pcd.service.client.AsyncRsData_Type asyncRsData;

    public Status_Type() {
    }

    public Status_Type(
           long statusCode,
           java.lang.String serverStatusCode,
           java.lang.String severity,
           java.lang.String statusDesc,
           com.cft.hogan.platform.ppm.api.pcd.service.client.SubjectElement_Type[] subjectElement,
           com.cft.hogan.platform.ppm.api.pcd.service.client.AdditionalStatus_Type[] additionalStatus,
           com.cft.hogan.platform.ppm.api.pcd.service.client.AsyncRsData_Type asyncRsData) {
           this.statusCode = statusCode;
           this.serverStatusCode = serverStatusCode;
           this.severity = severity;
           this.statusDesc = statusDesc;
           this.subjectElement = subjectElement;
           this.additionalStatus = additionalStatus;
           this.asyncRsData = asyncRsData;
    }


    /**
     * Gets the statusCode value for this Status_Type.
     * 
     * @return statusCode
     */
    public long getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this Status_Type.
     * 
     * @param statusCode
     */
    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the serverStatusCode value for this Status_Type.
     * 
     * @return serverStatusCode
     */
    public java.lang.String getServerStatusCode() {
        return serverStatusCode;
    }


    /**
     * Sets the serverStatusCode value for this Status_Type.
     * 
     * @param serverStatusCode
     */
    public void setServerStatusCode(java.lang.String serverStatusCode) {
        this.serverStatusCode = serverStatusCode;
    }


    /**
     * Gets the severity value for this Status_Type.
     * 
     * @return severity
     */
    public java.lang.String getSeverity() {
        return severity;
    }


    /**
     * Sets the severity value for this Status_Type.
     * 
     * @param severity
     */
    public void setSeverity(java.lang.String severity) {
        this.severity = severity;
    }


    /**
     * Gets the statusDesc value for this Status_Type.
     * 
     * @return statusDesc
     */
    public java.lang.String getStatusDesc() {
        return statusDesc;
    }


    /**
     * Sets the statusDesc value for this Status_Type.
     * 
     * @param statusDesc
     */
    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }


    /**
     * Gets the subjectElement value for this Status_Type.
     * 
     * @return subjectElement
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.SubjectElement_Type[] getSubjectElement() {
        return subjectElement;
    }


    /**
     * Sets the subjectElement value for this Status_Type.
     * 
     * @param subjectElement
     */
    public void setSubjectElement(com.cft.hogan.platform.ppm.api.pcd.service.client.SubjectElement_Type[] subjectElement) {
        this.subjectElement = subjectElement;
    }

    public com.cft.hogan.platform.ppm.api.pcd.service.client.SubjectElement_Type getSubjectElement(int i) {
        return this.subjectElement[i];
    }

    public void setSubjectElement(int i, com.cft.hogan.platform.ppm.api.pcd.service.client.SubjectElement_Type _value) {
        this.subjectElement[i] = _value;
    }


    /**
     * Gets the additionalStatus value for this Status_Type.
     * 
     * @return additionalStatus
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.AdditionalStatus_Type[] getAdditionalStatus() {
        return additionalStatus;
    }


    /**
     * Sets the additionalStatus value for this Status_Type.
     * 
     * @param additionalStatus
     */
    public void setAdditionalStatus(com.cft.hogan.platform.ppm.api.pcd.service.client.AdditionalStatus_Type[] additionalStatus) {
        this.additionalStatus = additionalStatus;
    }

    public com.cft.hogan.platform.ppm.api.pcd.service.client.AdditionalStatus_Type getAdditionalStatus(int i) {
        return this.additionalStatus[i];
    }

    public void setAdditionalStatus(int i, com.cft.hogan.platform.ppm.api.pcd.service.client.AdditionalStatus_Type _value) {
        this.additionalStatus[i] = _value;
    }


    /**
     * Gets the asyncRsData value for this Status_Type.
     * 
     * @return asyncRsData
     */
    public com.cft.hogan.platform.ppm.api.pcd.service.client.AsyncRsData_Type getAsyncRsData() {
        return asyncRsData;
    }


    /**
     * Sets the asyncRsData value for this Status_Type.
     * 
     * @param asyncRsData
     */
    public void setAsyncRsData(com.cft.hogan.platform.ppm.api.pcd.service.client.AsyncRsData_Type asyncRsData) {
        this.asyncRsData = asyncRsData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Status_Type)) return false;
        Status_Type other = (Status_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.statusCode == other.getStatusCode() &&
            ((this.serverStatusCode==null && other.getServerStatusCode()==null) || 
             (this.serverStatusCode!=null &&
              this.serverStatusCode.equals(other.getServerStatusCode()))) &&
            ((this.severity==null && other.getSeverity()==null) || 
             (this.severity!=null &&
              this.severity.equals(other.getSeverity()))) &&
            ((this.statusDesc==null && other.getStatusDesc()==null) || 
             (this.statusDesc!=null &&
              this.statusDesc.equals(other.getStatusDesc()))) &&
            ((this.subjectElement==null && other.getSubjectElement()==null) || 
             (this.subjectElement!=null &&
              java.util.Arrays.equals(this.subjectElement, other.getSubjectElement()))) &&
            ((this.additionalStatus==null && other.getAdditionalStatus()==null) || 
             (this.additionalStatus!=null &&
              java.util.Arrays.equals(this.additionalStatus, other.getAdditionalStatus()))) &&
            ((this.asyncRsData==null && other.getAsyncRsData()==null) || 
             (this.asyncRsData!=null &&
              this.asyncRsData.equals(other.getAsyncRsData())));
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
        _hashCode += new Long(getStatusCode()).hashCode();
        if (getServerStatusCode() != null) {
            _hashCode += getServerStatusCode().hashCode();
        }
        if (getSeverity() != null) {
            _hashCode += getSeverity().hashCode();
        }
        if (getStatusDesc() != null) {
            _hashCode += getStatusDesc().hashCode();
        }
        if (getSubjectElement() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubjectElement());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubjectElement(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAdditionalStatus() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdditionalStatus());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdditionalStatus(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAsyncRsData() != null) {
            _hashCode += getAsyncRsData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Status_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "Status_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ServerStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("severity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Severity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "ClosedEnum_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subjectElement");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SubjectElement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "SubjectElement_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AdditionalStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "AdditionalStatus_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asyncRsData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AsyncRsData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "AsyncRsData_Type"));
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
