<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example:
	<componentlist>
		<component>
			<id>1</id>
			<manufacturer>Intel</manufacturer>
			<type>Pentium 2 800 MHz</type> 
			<quantity>234</quantity>
			<price>23</price>
		</component>
	</componentlist>
-->
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	
	<xsl:output method="html" />
	<xsl:template match="componentlist">
		<table border="0">
			<tr bgcolor="silver" cellspacing="0">
				<td>
					<strong>Manufacturer</strong>
				</td>
				<td>
					<strong>Type</strong>
				</td>
				<td>
					<strong>Quantity</strong>
				</td>
				<td>
					<strong>Price</strong>
				</td>
			</tr>
			<xsl:apply-templates select="component" />
		</table>
	</xsl:template>

	<xsl:template match="component">
		<form action="manager" method="post">
			<tr bgcolor="#FFDC75">
				<td>
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="manufacturer" />
						</xsl:attribute>
						<xsl:attribute name="name">manufacturer</xsl:attribute>
					</xsl:element>
				</td>
				<td>
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="type" />
						</xsl:attribute>
						<xsl:attribute name="name">type</xsl:attribute>
					</xsl:element>
				</td>
				<td>
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="quantity" />
						</xsl:attribute>
						<xsl:attribute name="name">quantity</xsl:attribute>
					</xsl:element>
				</td>
				<td>
					<xsl:element name="input">
						<xsl:attribute name="type">text</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="price" />
						</xsl:attribute>
						<xsl:attribute name="name">price</xsl:attribute>
					</xsl:element>
				</td>
				<td>
					<input type="submit" value="UPDATE" />
				</td>
			</tr>
			
			<xsl:element name="input">
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="id" />
				</xsl:attribute>
				<xsl:attribute name="name">componentid</xsl:attribute>
			</xsl:element>
			
			<input type="hidden" name="action" value="update" />
			
		</form>
	</xsl:template>

</xsl:stylesheet>


