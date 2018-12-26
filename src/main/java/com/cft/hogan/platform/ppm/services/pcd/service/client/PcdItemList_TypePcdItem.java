/**
 * PcdItemList_TypePcdItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class PcdItemList_TypePcdItem  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey[] pcdItemKey;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type pcdData;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdWorkArea pcdWorkArea;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type cdmfRegKey;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type cdmfCdkKey;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type cdmfSimKey;

    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry pcdEntry;

    public PcdItemList_TypePcdItem() {
    }

    public PcdItemList_TypePcdItem(
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey[] pcdItemKey,
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type pcdData,
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdWorkArea pcdWorkArea,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type cdmfRegKey,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type cdmfCdkKey,
           com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type cdmfSimKey,
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry pcdEntry) {
           this.pcdItemKey = pcdItemKey;
           this.pcdData = pcdData;
           this.pcdWorkArea = pcdWorkArea;
           this.cdmfKeyInfo = cdmfKeyInfo;
           this.cdmfRegKey = cdmfRegKey;
           this.cdmfCdkKey = cdmfCdkKey;
           this.cdmfSimKey = cdmfSimKey;
           this.pcdEntry = pcdEntry;
    }


    /**
     * Gets the pcdItemKey value for this PcdItemList_TypePcdItem.
     * 
     * @return pcdItemKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey[] getPcdItemKey() {
        return pcdItemKey;
    }


    /**
     * Sets the pcdItemKey value for this PcdItemList_TypePcdItem.
     * 
     * @param pcdItemKey
     */
    public void setPcdItemKey(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey[] pcdItemKey) {
        this.pcdItemKey = pcdItemKey;
    }

    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey getPcdItemKey(int i) {
        return this.pcdItemKey[i];
    }

    public void setPcdItemKey(int i, com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItemPcdItemKey _value) {
        this.pcdItemKey[i] = _value;
    }


    /**
     * Gets the pcdData value for this PcdItemList_TypePcdItem.
     * 
     * @return pcdData
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type getPcdData() {
        return pcdData;
    }


    /**
     * Sets the pcdData value for this PcdItemList_TypePcdItem.
     * 
     * @param pcdData
     */
    public void setPcdData(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdData_Type pcdData) {
        this.pcdData = pcdData;
    }


    /**
     * Gets the pcdWorkArea value for this PcdItemList_TypePcdItem.
     * 
     * @return pcdWorkArea
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdWorkArea getPcdWorkArea() {
        return pcdWorkArea;
    }


    /**
     * Sets the pcdWorkArea value for this PcdItemList_TypePcdItem.
     * 
     * @param pcdWorkArea
     */
    public void setPcdWorkArea(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdWorkArea pcdWorkArea) {
        this.pcdWorkArea = pcdWorkArea;
    }


    /**
     * Gets the cdmfKeyInfo value for this PcdItemList_TypePcdItem.
     * 
     * @return cdmfKeyInfo
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type getCdmfKeyInfo() {
        return cdmfKeyInfo;
    }


    /**
     * Sets the cdmfKeyInfo value for this PcdItemList_TypePcdItem.
     * 
     * @param cdmfKeyInfo
     */
    public void setCdmfKeyInfo(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfKeyInfo_Type cdmfKeyInfo) {
        this.cdmfKeyInfo = cdmfKeyInfo;
    }


    /**
     * Gets the cdmfRegKey value for this PcdItemList_TypePcdItem.
     * 
     * @return cdmfRegKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type getCdmfRegKey() {
        return cdmfRegKey;
    }


    /**
     * Sets the cdmfRegKey value for this PcdItemList_TypePcdItem.
     * 
     * @param cdmfRegKey
     */
    public void setCdmfRegKey(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfRegKey_Type cdmfRegKey) {
        this.cdmfRegKey = cdmfRegKey;
    }


    /**
     * Gets the cdmfCdkKey value for this PcdItemList_TypePcdItem.
     * 
     * @return cdmfCdkKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type getCdmfCdkKey() {
        return cdmfCdkKey;
    }


    /**
     * Sets the cdmfCdkKey value for this PcdItemList_TypePcdItem.
     * 
     * @param cdmfCdkKey
     */
    public void setCdmfCdkKey(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfCdkKey_Type cdmfCdkKey) {
        this.cdmfCdkKey = cdmfCdkKey;
    }


    /**
     * Gets the cdmfSimKey value for this PcdItemList_TypePcdItem.
     * 
     * @return cdmfSimKey
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type getCdmfSimKey() {
        return cdmfSimKey;
    }


    /**
     * Sets the cdmfSimKey value for this PcdItemList_TypePcdItem.
     * 
     * @param cdmfSimKey
     */
    public void setCdmfSimKey(com.cft.hogan.platform.ppm.services.pcd.service.client.CdmfSimKey_Type cdmfSimKey) {
        this.cdmfSimKey = cdmfSimKey;
    }


    /**
     * Gets the pcdEntry value for this PcdItemList_TypePcdItem.
     * 
     * @return pcdEntry
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry getPcdEntry() {
        return pcdEntry;
    }


    /**
     * Sets the pcdEntry value for this PcdItemList_TypePcdItem.
     * 
     * @param pcdEntry
     */
    public void setPcdEntry(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdEntry pcdEntry) {
        this.pcdEntry = pcdEntry;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PcdItemList_TypePcdItem)) return false;
        PcdItemList_TypePcdItem other = (PcdItemList_TypePcdItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pcdItemKey==null && other.getPcdItemKey()==null) || 
             (this.pcdItemKey!=null &&
              java.util.Arrays.equals(this.pcdItemKey, other.getPcdItemKey()))) &&
            ((this.pcdData==null && other.getPcdData()==null) || 
             (this.pcdData!=null &&
              this.pcdData.equals(other.getPcdData()))) &&
            ((this.pcdWorkArea==null && other.getPcdWorkArea()==null) || 
             (this.pcdWorkArea!=null &&
              this.pcdWorkArea.equals(other.getPcdWorkArea()))) &&
            ((this.cdmfKeyInfo==null && other.getCdmfKeyInfo()==null) || 
             (this.cdmfKeyInfo!=null &&
              this.cdmfKeyInfo.equals(other.getCdmfKeyInfo()))) &&
            ((this.cdmfRegKey==null && other.getCdmfRegKey()==null) || 
             (this.cdmfRegKey!=null &&
              this.cdmfRegKey.equals(other.getCdmfRegKey()))) &&
            ((this.cdmfCdkKey==null && other.getCdmfCdkKey()==null) || 
             (this.cdmfCdkKey!=null &&
              this.cdmfCdkKey.equals(other.getCdmfCdkKey()))) &&
            ((this.cdmfSimKey==null && other.getCdmfSimKey()==null) || 
             (this.cdmfSimKey!=null &&
              this.cdmfSimKey.equals(other.getCdmfSimKey()))) &&
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
        if (getPcdItemKey() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPcdItemKey());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPcdItemKey(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPcdData() != null) {
            _hashCode += getPcdData().hashCode();
        }
        if (getPcdWorkArea() != null) {
            _hashCode += getPcdWorkArea().hashCode();
        }
        if (getCdmfKeyInfo() != null) {
            _hashCode += getCdmfKeyInfo().hashCode();
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
        if (getPcdEntry() != null) {
            _hashCode += getPcdEntry().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PcdItemList_TypePcdItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", ">PcdItemList_Type>PcdItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdItemKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdItemKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", ">>PcdItemList_Type>PcdItem>PcdItemKey"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdData_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdWorkArea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdWorkArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdWorkArea"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfKeyInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfKeyInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfKeyInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfRegKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfRegKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfRegKey_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfCdkKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfCdkKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfCdkKey_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdmfSimKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CdmfSimKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "CdmfSimKey_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdEntry");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdEntry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdEntry"));
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
