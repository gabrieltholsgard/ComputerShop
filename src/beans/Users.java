/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 * 
 * @author staffan
 */

public class Users {

	private String userName;

	private String userPass;

	private String name;

	private String streetAddress;

	private String zipCode;

	private String city;

	private String country;

	public Users() {
	}

	public Users(String userName) {
		this.userName = userName;
	}

	public Users(String userName, String userPass, String name,
			String streetAddress, String zipCode, String city, String country) {
		this.userName = userName;
		this.userPass = userPass;
		this.name = name;
		this.streetAddress = streetAddress;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userName != null ? userName.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Users)) {
			return false;
		}
		Users other = (Users) object;
		if ((this.userName == null && other.userName != null)
				|| (this.userName != null && !this.userName
						.equals(other.userName))) {
			return false;
		}
		return true;
	}

	public String getXml() {
		StringBuffer xmlOut = new StringBuffer();

		xmlOut.append("<user>");
		xmlOut.append("<uname>");
		xmlOut.append(this.userName);
		xmlOut.append("</uname>");
		xmlOut.append("<upass>");
		xmlOut.append(this.userPass);
		xmlOut.append("</upass>");
		xmlOut.append("<name>");
		xmlOut.append(this.name);
		xmlOut.append("</name>");
		xmlOut.append("<saddress>");
		xmlOut.append(this.streetAddress);
		xmlOut.append("</saddress>");
		xmlOut.append("<zcode>");
		xmlOut.append(this.zipCode);
		xmlOut.append("</zcode>");
		xmlOut.append("<city>");
		xmlOut.append(this.city);
		xmlOut.append("</city>");
		xmlOut.append("<country>");
		xmlOut.append(this.country);
		xmlOut.append("</country>");
		xmlOut.append("</user>");
		return xmlOut.toString();
	}

}
