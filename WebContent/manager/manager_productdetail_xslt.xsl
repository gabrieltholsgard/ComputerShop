<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
<detailpage>
	<product>
		<id>1</id>
		<prod>ASUS U36J</prod>
		<price>23</price>
		<profit>10</profit>
		<visible>1</visible>
		<description>Bla bla bla</description>
		<component>*
			<id>1</id>
			<manufacturer>Intel</manufacturer>
			<type>Pentium 2 800MHz</type>
			<quantity>23</quantity>
			<price>231</price>
			<needed>2</needed>
		</component>
	</product>
</detailpage>
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="html" />
	<xsl:template match="detailpage">
		<html>
			<head>
				<title>ComputerShop::Product Detail</title>
			</head>
			<body>
				<h2>Product detail</h2>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="product">
		<table border="0">
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">Product</td>
				<td>
					<xsl:value-of select="prod" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">Price</td>
				<td>
					<xsl:value-of select="price" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">Description</td>
				<td>
					<xsl:value-of select="description" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">Components</td>
				<td>
					<table>
						<tr bgcolor="silver" align="center">
							<td>Manufacturer</td>
							<td>Type</td>
							<td>Have</td>
							<td>Quantity</td>
						</tr>
						<xsl:param name="prodid" select="id" />
						<xsl:for-each select="component">
						<form action="manager?action=removeComponent" method="post">
							<tr align="center">
								<td bgcolor="#FFDC75">
									<xsl:value-of select="manufacturer" />
								</td>
								<td bgcolor="#FFDC75">
									<xsl:value-of select="type" />
								</td>
								<td bgcolor="#FFDC75">
									<xsl:value-of select="needed" />
								</td>
								<td bgcolor="#FFDC75">
									<select name="quantity">
										<xsl:call-template name="quantityloop" />
									</select>
								</td>
								<td>
									<input type="submit" value="Remove" />
								</td>
							</tr>
							<xsl:element name="input">
								<xsl:attribute name="type">hidden</xsl:attribute>
								<xsl:attribute name="name">componentid</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="id" />
								</xsl:attribute>
							</xsl:element>
							<xsl:element name="input">
								<xsl:attribute name="type">hidden</xsl:attribute>
								<xsl:attribute name="name">productid</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="$prodid"/>
								</xsl:attribute>
							</xsl:element>
						</form>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</table>
	</xsl:template>

	
	<xsl:template name="quantityloop">
        <xsl:param name="index" select="1" />
        <xsl:param name="total" select="needed" />
        
        <xsl:element name="option">
        	<xsl:attribute name="value">
        		<xsl:value-of select="$index" />
        	</xsl:attribute>
        	<xsl:value-of select="$index" />
        </xsl:element>
        <xsl:if test="not($total = 0)">
        <xsl:if test="not($index = $total)">
            <xsl:call-template name="quantityloop">
            	<xsl:with-param name="index" select="$index + 1" />
           	</xsl:call-template>
        </xsl:if>
        </xsl:if>
	</xsl:template>

</xsl:stylesheet>
