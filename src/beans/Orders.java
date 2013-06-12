/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author staffan
 */

public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer orderId;
    
    private String buyerName;
   
    private String shippingAdress;
   
    private String shippingZipcode;
   
    private String shippingCity;
   
    private HashMap<BookBean, Integer> orderItemsCollection;

    public Orders() {
    	this.orderItemsCollection = new HashMap<BookBean, Integer>();
    }

    public Orders(Integer orderId) {
    	this.orderItemsCollection = new HashMap<BookBean, Integer>();
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public String getShippingZipcode() {
        return shippingZipcode;
    }

    public void setShippingZipcode(String shippingZipcode) {
        this.shippingZipcode = shippingZipcode;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    @XmlTransient
    public HashMap<BookBean, Integer> getOrderItemsCollection() {
        return orderItemsCollection;
    }

    public void setOrderItemsCollection(HashMap<BookBean, Integer> orderItemsCollection) {
        this.orderItemsCollection = orderItemsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Orders[ orderId=" + orderId + " ]";
    }
    public String getXml() {
		StringBuffer xmlOut = new StringBuffer();
		
		

		xmlOut.append("<order>");
		xmlOut.append("<orderId>");
		xmlOut.append(this.orderId);
		xmlOut.append("</orderId>");
		xmlOut.append("<buyerName>");
		xmlOut.append(this.buyerName);
		xmlOut.append("</buyerName>");
		xmlOut.append("<shippingAdress>");
		xmlOut.append(this.shippingAdress);
		xmlOut.append("</shippingAdress>");
		xmlOut.append("<shippingZipcode>");
		xmlOut.append(this.shippingZipcode);
		xmlOut.append("</shippingZipcode>");
		xmlOut.append("<shippingCity>");
		xmlOut.append(this.shippingCity);
		xmlOut.append("</shippingCity>");
		xmlOut.append("</order>");
		return xmlOut.toString();
	}
}
