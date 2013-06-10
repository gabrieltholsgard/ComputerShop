<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
<productlist>
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
</productlist>
-->
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
					<strong>Visible</strong>
				</td>
				<td>
					<strong>Profit</strong>
				</td>
			</tr>
			<xsl:apply-templates />
		</table>
	</xsl:template>

	<xsl:template match="product">
		<form method="post" action="manager?action=update">
			<tr bgcolor="#FFDC75">
				<td>
					<table>
						<tr>
							<td>
								<xsl:element name="input">
									<xsl:attribute name="type">text</xsl:attribute>
									<xsl:attribute name="value">
										<xsl:value-of select="prod" />
									</xsl:attribute>
									<xsl:attribute name="name">product</xsl:attribute>
									<xsl:attribute name="size">120</xsl:attribute>
								</xsl:element>
							</td>
						</tr>
						<tr>
							<td>
								<strong>Description</strong>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:element name="textarea">
									<xsl:attribute name="rows">10</xsl:attribute>
									<xsl:attribute name="cols">10</xsl:attribute>
									<xsl:attribute name="name">description</xsl:attribute>
									<xsl:attribute name="style">width: 600; height: 50; resize: none;</xsl:attribute>
									<xsl:value-of select="description" />
								</xsl:element>
							 
							</td>
						</tr>
					</table>
				</td>
				<td>
					<xsl:element name="input">
						<xsl:attribute name="type">checkbox</xsl:attribute>
						<xsl:attribute name="value">true</xsl:attribute>
						<xsl:attribute name="name">visible</xsl:attribute>
						<xsl:if test="visible &gt; 0">
							<xsl:attribute name="checked"></xsl:attribute>
						</xsl:if>
					</xsl:element>
				</td>
				<td>
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="profit" />
						</xsl:attribute>
						<xsl:attribute name="name">profit</xsl:attribute>
					</xsl:element>
				</td>
				<td>
					<input type="submit" value="UPDATE" />
					<xsl:element name="a"> <!-- A link in XSLT -->
						<xsl:attribute name="href"><xsl:text
							disable-output-escaping="yes"><![CDATA[manager?action=detail&productid=]]></xsl:text><xsl:value-of
							select="id" />
						</xsl:attribute>
						<xsl:text>Detail</xsl:text>
					</xsl:element>
				</td>
			</tr>

			<xsl:element name="input"> <!--A ordinary input in XSLT -->
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="id" /></xsl:attribute>
				<xsl:attribute name="name">productid</xsl:attribute>
			</xsl:element>
		</form>
	</xsl:template>

</xsl:stylesheet>
