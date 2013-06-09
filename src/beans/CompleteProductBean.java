package beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CompleteProductBean {
	private int id;
	private String product;
	private String description;
	private int price;
	private Collection<ComponentBean> components;
	private int profit;
	private boolean visible;
	
	
	public CompleteProductBean() {
		this.components = new ArrayList<ComponentBean>();
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int _id) {
		this.id = _id;
	}
	
	
	public String getProduct() {
		return this.product;
	}
	
	public void setProduct(String _product) {
		this.product = _product;
	}
	
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String _description) {
		this.description = _description;
	}
	
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int _price) {
		this.price = _price;
	}
	
	
	public Collection<ComponentBean> getComponents() {
		return this.components;
	}
	
	public void setComponents(Collection<ComponentBean> _components) {
		this.components = _components;
	}

	
	public int getProfit() {
		return this.profit;
	}
	
	public void setProfit(int _profit) {
		this.profit = _profit;
	}
	
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public void setVisbile(boolean _visible) {
		this.visible = _visible;
	}
	
	
	public void addComponent(ComponentBean cb) {
		this.components.add(cb);
	}
	
	
	
	public String getXml() {
		StringBuffer xmlOut = new StringBuffer();
		
		xmlOut.append("<product>");
		xmlOut.append("<id>");
		xmlOut.append(this.id);
		xmlOut.append("</id>");
		xmlOut.append("<prod><![CDATA[");
		xmlOut.append(this.product);
		xmlOut.append("]]></prod>");
		xmlOut.append("<price>");
		xmlOut.append(this.price);
		xmlOut.append("</price>");
		xmlOut.append("<profit>");
		xmlOut.append(this.profit);
		xmlOut.append("</profit>");
		xmlOut.append("<visible>");
		if (this.visible)
			xmlOut.append(1);
		else
			xmlOut.append(0);
		xmlOut.append("</visible>");
		xmlOut.append("<description><![CDATA[");
		xmlOut.append(this.description);
		xmlOut.append("]]></description>");
		if (this.components != null) {
			Iterator<ComponentBean> iter = this.components.iterator();
			while (iter.hasNext()) {
				ComponentBean cb = iter.next();
				xmlOut.append(cb.getXml());
			}
		}
		xmlOut.append("</product>");
		return xmlOut.toString();
	}
	
	
}
