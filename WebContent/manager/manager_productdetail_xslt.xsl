<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
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
						<form action="manager=delete" method="post">
							<xsl:apply-templates select="component" />
							<xsl:element name="input">
								<xsl:attribute name="type">hidden</xsl:attribute>
								<xsl:attribute name="name">productid</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="id"/>
								</xsl:attribute>
							</xsl:element>
						</form>
						<tr>
							<form action="manager?action=addComponent" method="post">
								<td>
									<xsl:element name="input">
										<xsl:attribute name="type">text</xsl:attribute>
										<xsl:attribute name="size">20</xsl:attribute>
										<xsl:attribute name="name">component</xsl:attribute>
									</xsl:element>
								</td>
								<td>
									<xsl:element name="input">
										<xsl:attribute name="type">submit</xsl:attribute>
										<xsl:attribute name="value">Add</xsl:attribute>
									</xsl:element>
								</td>
								<xsl:element name="input">
									<xsl:attribute name="type">hidden</xsl:attribute>
									<xsl:attribute name="name">id</xsl:attribute>
									<xsl:attribute name="value">
										<xsl:value-of select="id" />
									</xsl:attribute>
								</xsl:element>
							</form>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<xsl:element name="a"> <!-- A link in XSLT -->
			<xsl:attribute name="href"><xsl:text>manager?action=manage</xsl:text></xsl:attribute>
			<xsl:text>Back</xsl:text>
		</xsl:element>
	</xsl:template>


	<xsl:template match="component">
		<tr>
			<td>
				<xsl:value-of select="manufacturer" />
				,
				<xsl:value-of select="type" />
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
		
	</xsl:template>

</xsl:stylesheet>
