package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ComponentListBean {
	
	private Collection<ComponentBean> componentList;
	private String url = null;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	
	public ComponentListBean() {
	}
	
	public ComponentListBean(String _url) throws Exception {
		this.componentList = new ArrayList<ComponentBean>();
		this.url = _url;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(this.url);
			
			this.stmt = this.conn.createStatement();
			String sql = "SELECT * FROM AUTHORS";
			this.rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ComponentBean cb = new ComponentBean();
				cb.setId(rs.getInt("AUTHOR_ID"));
				cb.setManufacturer(rs.getString("NAME"));
				cb.setType(rs.getString("SURNAME"));
				cb.setQuantity(rs.getInt("QTY"));
				cb.setPrice(rs.getInt("C_PRICE"));
				this.componentList.add(cb);
			}
			
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		}
		
		finally {
			try {
				rs.close();
			} catch (Exception e) {}
			try {
				stmt.close();
			} catch (Exception e) {}
			try {
				conn.close();
			} catch (Exception e) {}
		}
	}
	
	
	public Collection<ComponentBean> getComponentList() {
		return this.componentList;
	}
	
	
	public ComponentBean getById(int id) {
		ComponentBean tmp = null;
		Iterator<ComponentBean> iter = this.componentList.iterator();
		while(iter.hasNext()) {
			tmp = iter.next();
			if(tmp.getId() == id)
				return tmp;
		}
		return null;
	}
	
	
	
	public String getXml() {
		StringBuffer xmlOut = new StringBuffer();
		Iterator<ComponentBean> iter = this.componentList.iterator();
		xmlOut.append("<componentlist>");
		while(iter.hasNext()) {
			xmlOut.append(iter.next().getXml());
		}
		xmlOut.append("</componentlist>");
		return xmlOut.toString();
	}
	
	
}
