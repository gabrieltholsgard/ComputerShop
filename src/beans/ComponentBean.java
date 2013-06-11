package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ComponentBean {
	private int id;
	private String manufacturer;
	private String type;
	private int quantity;
	private int needed;
	private int price;
	private Connection con;
	private PreparedStatement orderPstmt;

	
	public ComponentBean() {
		needed = 0;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int _id) {
		this.id = _id;
	}
	
	public String getManufacturer() {
		return this.manufacturer;
	}
	
	public void setManufacturer(String _manufacturer) {
		this.manufacturer = _manufacturer;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String _type) {
		this.type = _type;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int _quantity) {
		this.quantity = _quantity;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int _price) {
		this.price = _price;
	}
	
	
	public int getNeeded() {
		return this.needed;
	}
	
	public void setNeeded(int _needed) {
		this.needed = _needed;
	}
	
	
	
	public String getXml() {
		StringBuffer xmlOut = new StringBuffer();
		
		xmlOut.append("<component>");
		xmlOut.append("<id>");
		xmlOut.append(this.id);
		xmlOut.append("</id>");
		xmlOut.append("<manufacturer>");
		xmlOut.append(this.manufacturer);
		xmlOut.append("</manufacturer>");
		xmlOut.append("<type>");
		xmlOut.append(this.type);
		xmlOut.append("</type>");
		xmlOut.append("<quantity>");
		xmlOut.append(this.quantity);
		xmlOut.append("</quantity>");
		xmlOut.append("<price>");
		xmlOut.append(this.price);
		xmlOut.append("</price>");
		xmlOut.append("<needed>");
		xmlOut.append(this.needed);
		xmlOut.append("</needed>");
		xmlOut.append("</component>");
		
		return xmlOut.toString();
	}
	
	
	public void add(String _url) {
		String orderSQL = "INSERT INTO AUTHORS(NAME,";
		orderSQL += " SURNAME, QTY, C_PRICE)";
		orderSQL += " VALUES(?,?,?,?)";
		try {
			// load the driver and get a connection
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(_url);
			// turn off auto commit to handle transactions yourself
			con.setAutoCommit(false);
			orderPstmt = con.prepareStatement(orderSQL);
			orderPstmt.setString(1, this.manufacturer);
			orderPstmt.setString(2, this.type);
			orderPstmt.setInt(3, this.quantity);
			orderPstmt.setInt(4, this.price);
			orderPstmt.execute();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback(); // failed, rollback the database
			} catch (Exception ee) {
			}
		} finally {

			try {
				orderPstmt.close();
			} catch (Exception e) {
			}

			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}
	
	
}
