<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
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
				<td bgcolor="#FFDC75" valign="top" align="left">Components</td>
				<td>
					<ul>
						<xsl:for-each select="comp">
							<li>
								<xsl:value-of select="manufacturer" />,
								<xsl:value-of select="type" />
							</li>
						</xsl:for-each>
					</ul>
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">Price</td>
				<td>
					<xsl:value-of select="price" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">In Stock</td>
				<td>
					<xsl:value-of select="available" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFDC75" valign="top" align="left">Description</td>
				<td>
					<xsl:value-of select="description" />
				</td>
			</tr>
		</table>
		<xsl:element name="a"> <!-- A link in XSLT -->
			<xsl:attribute name="href"><xsl:text>shop</xsl:text></xsl:attribute>
			<xsl:text>Back</xsl:text>
		</xsl:element>
		<xsl:text disable-output-escaping="yes">
			<![CDATA[&nbsp;&nbsp]]>
		</xsl:text>
		<xsl:element name="a"> <!-- A link in XSLT -->
			<xsl:attribute name="href"><xsl:text disable-output-escaping="yes"><![CDATA[shop?action=add&quantity=1&bookid=]]></xsl:text><xsl:value-of select="id" /></xsl:attribute>
			<xsl:text>Add one copy</xsl:text>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>
