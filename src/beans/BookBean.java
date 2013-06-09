/*
 * BookBean.java
 *
 */
package beans;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author  Fredrik �lund, Olle Eriksson
 */
public class BookBean {

    // describe a book
    
    private int id;
    private String product;
    private int price;
    private HashMap<Integer, Component> components;
    private int profit;
    private String description;
    private boolean visible;
    private int availability;
    
    
    /** Creates a new instance of BookBean */
    public BookBean() {
    	this.components = new HashMap<Integer, Component>();
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int _price) {
        price = _price;
    }
    
    public int getProfit() {
        return this.profit;
    }
    
    public void setProfit(int _profit) {
        this.profit = _profit;
    }
    
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(String _product) {
        product = _product;
    }
    
    public HashMap<Integer, Component> getComponents() {
    	return this.components;
    }
    
    public void setManufacturer(int _id, String _manufacturer) {
    	Component tmp;
    	if(components.containsKey(_id)) {
    		tmp = components.get(_id);
    		tmp.manufacturer = _manufacturer;
    	}
    	else {
    		tmp = new Component();
    		tmp.manufacturer = _manufacturer;
    		tmp.type = "";
    	}
    	components.put(_id, tmp);
    }
    
    public String getManufacturer(int _id) {
    	Component tmp = this.components.get(_id);
    	if(tmp != null)
    		return tmp.manufacturer;
        return null;
    }
    
    public void setType(int _id, String _type) {
    	Component tmp;
    	if(components.containsKey(_id)) {
    		tmp = components.get(_id);
    		tmp.type = _type;
    	}
    	else {
    		tmp = new Component();
    		tmp.type = _type;
    		tmp.manufacturer = "";
    	}
    	components.put(_id, tmp);
    }
    
    public String getType(int _id) {
    	Component tmp = this.components.get(_id);
    	if( tmp != null) {
    		return tmp.type;
    	}
        return null;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId( int _id) {
        id= _id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String _description) {
        description = _description;
    }
    
    public boolean getVisible() {
        return visible;
    }
    
    public void setVisible(boolean v) {
        visible = v;
    }
    
    public int getAvailability() {
        return availability;
    }
    
    public void setAvailability(int a) {
        availability = a;
    }

    // create an XML document describing the book
    
    public String getXml() {
     StringBuffer xmlOut = new StringBuffer();
      
      xmlOut.append("<product>");
      xmlOut.append("<id>");
      xmlOut.append(id);
      xmlOut.append("</id>");
      xmlOut.append("<prod><![CDATA[");
      xmlOut.append(this.product);
      xmlOut.append("]]></prod>");
      xmlOut.append("<available>");
      xmlOut.append(this.availability);
      xmlOut.append("</available>");
      xmlOut.append("<price>");
      xmlOut.append(price);      
      xmlOut.append("</price>");
      xmlOut.append("<profit>");
      xmlOut.append(profit);
      xmlOut.append("</profit>");
      xmlOut.append("<visible>");
      if(this.visible)
    	  xmlOut.append(1);
      else
    	  xmlOut.append(0);
      xmlOut.append("</visible>");
      xmlOut.append("<description><![CDATA[");
      xmlOut.append(description);      
      xmlOut.append("]]></description>");
      if(this.components != null) {
    	  Iterator<Entry<Integer, Component>> iter = this.components.entrySet().iterator();
    	  while(iter.hasNext()) {
    		  Entry<Integer, Component> e = iter.next();
    		  xmlOut.append("<comp>");
    		  xmlOut.append("<cid>");
    		  xmlOut.append(e.getKey());
    		  xmlOut.append("</cid>");
    		  xmlOut.append("<manufacturer>");
    		  xmlOut.append(e.getValue().manufacturer);
    		  xmlOut.append("</manufacturer>");
    		  xmlOut.append("<type>");
    		  xmlOut.append(e.getValue().type);
    		  xmlOut.append("</type>");
    		  xmlOut.append("</comp>");
    	  }
      }
      xmlOut.append("</product>");
      return xmlOut.toString();
    
        
    }   
}
