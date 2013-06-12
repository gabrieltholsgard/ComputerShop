package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class userlistBean {

	private HashMap<String, Users> userlist;
	private String url = null;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public userlistBean() {
	}

	public userlistBean(String _url) throws Exception {
		this.url = _url;
		initusers();

	}

	private void initusers() throws Exception {
		this.userlist = new HashMap<String, Users>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(this.url);

			this.stmt = this.conn.createStatement();
			String sql = "SELECT * FROM USERS";
			this.rs = stmt.executeQuery(sql);
			Statement stmt1 = this.conn.createStatement();
			String sql1 = "SELECT * FROM USER_ROLES WHERE USER_NAME ='";
			ResultSet rs1;
			while (rs.next()) {
				Users cb = new Users();
				cb.setUserName(rs.getString("USER_NAME"));
				cb.setName(rs.getString("NAME"));
				cb.setStreetAddress(rs.getString("STREET_ADDRESS"));
				cb.setUserPass(rs.getString("USER_PASS"));
				cb.setCity(rs.getString("CITY"));
				cb.setZipCode(rs.getString("ZIP_CODE"));
				cb.setCountry(rs.getString("COUNTRY"));
				rs1 = stmt1.executeQuery(sql1 + rs.getString("USER_NAME") +"'");
				while (rs1.next()){
					cb.addClearance(rs1.getString("ROLE_NAME"));
				}
				
				this.userlist.put(cb.getUserName(), cb);

			}

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		}

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

	public Collection<Users> getUserList() {
		return this.userlist.values();
	}

	public Users getById(int id) {
		return this.userlist.get(id);
	}

	public String getXml() throws Exception {
		initusers();
		StringBuffer xmlOut = new StringBuffer();
		Iterator<Users> iter = getUserList().iterator();
		xmlOut.append("<userlist>");
		while (iter.hasNext()) {
			xmlOut.append(iter.next().getXml());
		}
		xmlOut.append("</userlist>");
		return xmlOut.toString();
	}

}
