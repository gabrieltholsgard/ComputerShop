<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example: <productlist> <product> <id>1</id> <prod>Javaprogramming</prod> 
	<available>21</available> <price>23</price> <profit>10</profit> <visible>1</visible> 
	<description>Bla bla bla</description> <comp>* <cid>1</cid> <manufacturer>Intel</manufacturer> 
	<type>Pentium 2 800MHz</type> </comp> </product> </productlist> -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />

	<xsl:template match="productlist">
		<table border="0">
			<tr bgcolor="silver" cellspacing="0">
				<td>
					<strong>Product</strong>
				</td>
				<td>
					<strong>Price</strong>
				</td>
				<td>
					<strong>Ordered Quantity</strong>
				</td>
			</tr>
			<xsl:apply-templates />
		</table>
	</xsl:template>

	<xsl:template match="product">
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<form method="post" action="shop">
			<tr bgcolor="#FFDC75">
				<td>
					<table>
						<tr>
							<td>
								<xsl:value-of select="prod" />
							</td>
						</tr>
						<tr>
							<td>
								<strong>Description</strong>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:value-of select="description" />
							</td>
						</tr>
					</table>
				</td>
				<td align="center">
					<xsl:value-of select="price" />
				</td>



				<td>
					<center>
						<xsl:value-of select="q" />
					</center>
				</td>
				<td>

					<xsl:element name="a"> <!-- A link in XSLT -->
						<xsl:attribute name="href"><xsl:text
							disable-output-escaping="yes"><![CDATA[shop?action=detail&bookid=]]></xsl:text><xsl:value-of
							select="id" />
						</xsl:attribute>
						<xsl:text>Detail</xsl:text>
					</xsl:element>
				</td>
			</tr>

			<xsl:element name="input"> <!--A ordinary input in XSLT -->
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="id" /></xsl:attribute>
				<xsl:attribute name="name">bookid</xsl:attribute>
			</xsl:element>

			<input type="hidden" name="action" value="add" />
		</form>
	</xsl:template>

</xsl:stylesheet>
