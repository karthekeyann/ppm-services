/**
 * PcdItemList_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cft.hogan.platform.ppm.services.pcd.service.client;

public class PcdItemList_Type  implements java.io.Serializable {
    private com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem[] pcdItem;

    private java.lang.String moreItem;

    private java.lang.String itemCnt;

    public PcdItemList_Type() {
    }

    public PcdItemList_Type(
           com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem[] pcdItem,
           java.lang.String moreItem,
           java.lang.String itemCnt) {
           this.pcdItem = pcdItem;
           this.moreItem = moreItem;
           this.itemCnt = itemCnt;
    }


    /**
     * Gets the pcdItem value for this PcdItemList_Type.
     * 
     * @return pcdItem
     */
    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem[] getPcdItem() {
        return pcdItem;
    }


    /**
     * Sets the pcdItem value for this PcdItemList_Type.
     * 
     * @param pcdItem
     */
    public void setPcdItem(com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem[] pcdItem) {
        this.pcdItem = pcdItem;
    }

    public com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem getPcdItem(int i) {
        return this.pcdItem[i];
    }

    public void setPcdItem(int i, com.cft.hogan.platform.ppm.services.pcd.service.client.PcdItemList_TypePcdItem _value) {
        this.pcdItem[i] = _value;
    }


    /**
     * Gets the moreItem value for this PcdItemList_Type.
     * 
     * @return moreItem
     */
    public java.lang.String getMoreItem() {
        return moreItem;
    }


    /**
     * Sets the moreItem value for this PcdItemList_Type.
     * 
     * @param moreItem
     */
    public void setMoreItem(java.lang.String moreItem) {
        this.moreItem = moreItem;
    }


    /**
     * Gets the itemCnt value for this PcdItemList_Type.
     * 
     * @return itemCnt
     */
    public java.lang.String getItemCnt() {
        return itemCnt;
    }


    /**
     * Sets the itemCnt value for this PcdItemList_Type.
     * 
     * @param itemCnt
     */
    public void setItemCnt(java.lang.String itemCnt) {
        this.itemCnt = itemCnt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PcdItemList_Type)) return false;
        PcdItemList_Type other = (PcdItemList_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pcdItem==null && other.getPcdItem()==null) || 
             (this.pcdItem!=null &&
              java.util.Arrays.equals(this.pcdItem, other.getPcdItem()))) &&
            ((this.moreItem==null && other.getMoreItem()==null) || 
             (this.moreItem!=null &&
              this.moreItem.equals(other.getMoreItem()))) &&
            ((this.itemCnt==null && other.getItemCnt()==null) || 
             (this.itemCnt!=null &&
              this.itemCnt.equals(other.getItemCnt())));
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
        if (getPcdItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPcdItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPcdItem(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMoreItem() != null) {
            _hashCode += getMoreItem().hashCode();
        }
        if (getItemCnt() != null) {
            _hashCode += getItemCnt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PcdItemList_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://MessageView/", "PcdItemList_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcdItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PcdItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://MessageView/", ">PcdItemList_Type>PcdItem"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("moreItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MoreItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemCnt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ItemCnt"));
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
