package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CompleteProductListBean {
	private Collection<CompleteProductBean> productList;
	private String url = null;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	
	
	public CompleteProductListBean() {}
	
	public CompleteProductListBean(String _url) throws Exception {
		this.productList = new ArrayList<CompleteProductBean>();
		this.url = _url;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(this.url);
			
			this.stmt = this.conn.createStatement();
			String sql = "SELECT B.BOOK_ID, B.TITLE, B.DESCRIPTION, B.PRICE, ";
			sql += "B.PROFIT, B.VISIBLE, A.AUTHOR_ID, A.NAME, A.SURNAME, ";
			sql += "A.QTY, A.C_PRICE, C.QTY AS NEEDED ";
			sql += "FROM BOOKS AS B, AUTHORS AS A, COMPOSITION AS C WHERE ";
			sql += "B.BOOK_ID = C.EL_ID AND ";
			sql += "A.AUTHOR_ID = C.COM_ID";
			this.rs = this.stmt.executeQuery(sql);
			
			int pid = -1;
			CompleteProductBean cpb = null;
			while(this.rs.next()) {
				if(pid != rs.getInt("BOOK_ID")) {
					cpb = new CompleteProductBean();
					pid = rs.getInt("BOOK_ID");
					cpb.setId(pid);
					cpb.setProduct(rs.getString("TITLE"));
					cpb.setDescription(rs.getString("DESCRIPTION"));
					cpb.setPrice(rs.getInt("PRICE"));
					cpb.setProfit(rs.getInt("PROFIT"));
					cpb.setVisbile(rs.getBoolean("VISIBLE"));
					ComponentBean cb = new ComponentBean();
					cb.setId(rs.getInt("AUTHOR_ID"));
					cb.setManufacturer(rs.getString("NAME"));
					cb.setType(rs.getString("SURNAME"));
					cb.setQuantity(rs.getInt("QTY"));
					cb.setPrice(rs.getInt("C_PRICE"));
					cb.setNeeded(rs.getInt("NEEDED"));
					cpb.addComponent(cb);
					this.productList.add(cpb);
				}
				else {
					ComponentBean cb = new ComponentBean();
					cb.setId(rs.getInt("AUTHOR_ID"));
					cb.setManufacturer(rs.getString("NAME"));
					cb.setType(rs.getString("SURNAME"));
					cb.setQuantity(rs.getInt("QTY"));
					cb.setPrice(rs.getInt("C_PRICE"));
					cb.setNeeded(rs.getInt("NEEDED"));
					cpb.addComponent(cb);
				}
			}
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		}
		
		finally {
			try {
				this.rs.close();
			} catch (Exception e) {}
			try {
				this.stmt.close();
			} catch (Exception e) {}
			try {
				this.conn.close();
			} catch (Exception e) {}
		}
	}
	
	
	
	public Collection<CompleteProductBean> getProductList() {
		return this.productList;
	}
	
	
	
	public String getXml() {
		StringBuffer xmlOut = new StringBuffer();
		Iterator<CompleteProductBean> iter = this.productList.iterator();
		CompleteProductBean cpb = null;
		
		xmlOut.append("<productlist>");
		while(iter.hasNext()) {
			cpb = iter.next();
			xmlOut.append(cpb.getXml());
		}
		xmlOut.append("</productlist>");
		return xmlOut.toString();
	}
	
	
	
	public CompleteProductBean getById(int id) {
		CompleteProductBean cpb = null;
		Iterator<CompleteProductBean> iter = this.productList.iterator();
		while(iter.hasNext()) {
			cpb = iter.next();
			if(cpb.getId() == id)
				return cpb;
		}
		return null;
	}
	
	
}
