<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
<productlist>
	<product>
		<id>1</id>
		<prod>Javaprogramming</prod>
		<available>21</available>
		<price>23</price>
		<profit>10</profit>
		<visible>1</visible>
		<description>Bla bla bla</description>
		<comp>*
			<cid>1</cid>
			<manufacturer>Intel</manufacturer>
			<type>Pentium 2 800MHz</type>
		</comp>
	</product>
</productlist>
-->
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	
	<xsl:output method="html" />
	
	<xsl:template match="orderlist">
	
		<table border="0">
			<tr bgcolor="silver" cellspacing="0">
				<td>
					<strong>ORDER_ID</strong>
				</td>
				<td>
					<strong>BUYER_NAME</strong>
				</td>
				<td>
					<strong>SHIPPING_ADDRESS</strong>
				</td>
				<td>
					<strong>SHIPPING_ZIPCODE</strong>
				</td>
				<td>
					<strong>SHIPPING_CITY</strong>
				</td>
			</tr>
			<xsl:apply-templates select="order" />
		</table>
	</xsl:template>
	
	

	<xsl:template match="order">
		<form action="manager?action=orderdetail" method="post">
			<tr bgcolor="#FFDC75">
				<td>
					<xsl:value-of select="orderId"/>
					
				</td>
				<td>
					<xsl:value-of select="buyerName"/>
					
				</td>
				<td>
					<xsl:value-of select="shippingAddress"/>
					
				</td>
				<td>
					<xsl:value-of select="shippingZipcode"/>
					
				</td>
				<td>
					<xsl:value-of select="shippingCity"/>
					
				</td>
				<td>
					<input type="submit" value="DETAIL"/>
				</td>
			</tr>			
			<xsl:element name="input">
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="orderId" />
				</xsl:attribute>
				<xsl:attribute name="name">orderid</xsl:attribute>
			</xsl:element>
			
		</form>
	</xsl:template>

</xsl:stylesheet>


