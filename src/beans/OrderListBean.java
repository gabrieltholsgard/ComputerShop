/*
 * BookListBean.java
 *
 */
package beans;

import java.util.*;
import java.sql.*;

/**
 * 
 * @author Fredrik �lund, Olle Eriksson
 */
public class OrderListBean {

	private Collection<Orders> orderlist;
	private String url = null;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	// this constructor is not really used in the application
	// but is here for testing purpose
	public OrderListBean() {
	}

	private void initBeans() throws Exception {
		orderlist = new ArrayList<Orders>(); // a list
		try {

			// get a database connection and load the JDBC-driver

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);

			// create SQL statements to load the books into the list
			// each book is a BookBean object

			stmt = conn.createStatement();
			String sql = "SELECT * FROM ORDERS";
			rs = stmt.executeQuery(sql);
			String sql1 = "SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			ResultSet rs1;
			Orders bb = null;
			BookListBean bl = new BookListBean(url);
			while (rs.next()) {
				bb = new Orders();
				bb.setOrderId(rs.getInt("ORDER_ID"));
				bb.setShippingAdress(rs.getString("SHIPPING_ADRESS"));
				bb.setBuyerName(rs.getString("BUYER_NAME"));
				bb.setShippingCity(rs.getString("SHIPPING_CITY"));
				bb.setShippingZipcode(rs.getString("SHIPPING_ZIPCODE"));
				stmt1.setInt(1, rs.getInt("ORDER_ID"));
				rs1 = stmt1.executeQuery();
				while (rs1.next()) {
					BookBean bbe = bl.getById(rs1.getInt("BOOK_ID"));
					bbe.q = rs1.getInt("QUANTITY");
					bb.getOrderItemsCollection().add(bbe);
				}
				orderlist.add(bb);
			}

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} // note the we always try to close all services
			// even if one or more fail to close
		finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	public Orders getById(int id) {
		Orders bb = null;
		Iterator<Orders> iter = orderlist.iterator();

		while (iter.hasNext()) {
			bb = iter.next();
			if (bb.getOrderId() == id) {
				return bb;
			}
		}
		return null;
	}

	/**
	 * Creates a new instance of BookListBean
	 */
	public OrderListBean(String _url) throws Exception {
		this.url = _url;
		initBeans();

	}

	// return the booklist
	public java.util.Collection<Orders> getProduktLista() {
		return orderlist;
	}

	// create an XML document from the booklist
	public String getXml() throws Exception {
		initBeans();
		Orders bb = null;
		Iterator<Orders> iter = orderlist.iterator();
		StringBuffer buff = new StringBuffer();

		buff.append("<orderlist>");
		while (iter.hasNext()) {
			bb = iter.next();
			buff.append(bb.getXml());
		}
		buff.append("</orderlist>");

		return buff.toString();
	}

	// search for a book by book ID

	// a main used for testing, remember that a bean can be run
	// without a container
	public static void main(String[] args) {
		try {
			BookListBean blb = new BookListBean();
			System.out.println(blb.getXml());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
