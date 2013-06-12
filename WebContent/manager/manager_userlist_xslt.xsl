<?xml version="1.0" encoding="UTF-8" ?>
<!-- Example: <productlist> <product> <id>1</id> <prod>Javaprogramming</prod> 
	<available>21</available> <price>23</price> <profit>10</profit> <visible>1</visible> 
	<description>Bla bla bla</description> <comp>* <cid>1</cid> <manufacturer>Intel</manufacturer> 
	<type>Pentium 2 800MHz</type> </comp> </product> </productlist> -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />

	<xsl:template match="userlist">

		<table border="0">
			<tr bgcolor="silver" cellspacing="0">
				<td>
					<strong>Username</strong>
				</td>
				<td>
					<strong>Name</strong>
				</td>
				<td>
					<strong>Street Address</strong>
				</td>
				<td>
					<strong>Zip code</strong>
				</td>
				<td>
					<strong>City</strong>
				</td>
				<td>
					<strong>Country</strong>
				</td>
				<td>
					<strong>Clearances</strong>
				</td>
			</tr>
			<xsl:apply-templates select="user" />
		</table>
	</xsl:template>



	<xsl:template match="user">
		<form action="manager?action=deleteuser" method="post">
			<tr bgcolor="#FFDC75">
				<td>
					<xsl:value-of select="uname" />

				</td>
				<td>
					<xsl:value-of select="name" />

				</td>
				<td>
					<xsl:value-of select="saddress" />

				</td>
				<td>
					<xsl:value-of select="zcode" />

				</td>
				<td>
					<xsl:value-of select="city" />

				</td>
				<td>
					<xsl:value-of select="country" />

				</td>
				<td>
					<table>
						<xsl:for-each select="clearance">
							<tr>
								<td>
									<xsl:value-of select="type" />
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
				<td>
					<input type="submit" value="DELETE" />
				</td>
			</tr>
			<input type="hidden" name="action" value="delete" />
			<xsl:element name="input"> 
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="uname" /></xsl:attribute>
				<xsl:attribute name="name">uname</xsl:attribute>
			</xsl:element>
		</form>
	</xsl:template>


</xsl:stylesheet>


