<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, tags.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="bookshop" uri="/bookshop"%>


<jsp:useBean id="product" class="beans.CompleteProductBean" scope="request">
    Error, the bean should have been created in the servlet!
</jsp:useBean>


<c:set var="manager_productdetail_xslt">
   <c:import url="manager_productdetail_xslt.xsl"/>
</c:set> 

<x:transform xslt="${manager_productdetail_xslt}">
    <detailpage>
      <jsp:getProperty name="product" property="xml"/>
    </detailpage>
</x:transform>


<p>
<p>

<form action="manager?action=manage" method="post">
	<input type="submit" value="Back" />
</form>

<form action="manager" method="post">
	<input type="submit" value="Main Menu" />
</form>


<jsp:useBean id="componentList" class="beans.ComponentListBean" scope="application">
	Error, the bean should have been created in the servlet!
</jsp:useBean>


<h2>Add Component</h2>

<form action="manager?action=addComponent" method="post">
	<table>
		<tr bgcolor="silver">
			<td><strong>Manufacturer, Type</strong></td>
			<td><strong>Quantity</strong></td>
		</tr>
		<tr>
			<td bgcolor="#FFDC75">
				<select name="componentid">
					<c:forEach var="component"
						items="${componentList.getComponentList()}">
						<option value="${component.id}">
							${component.manufacturer}, ${component.type}
						</option>
					</c:forEach>
				</select>
			</td>
			<td bgcolor="#FFDC75">
				<select name="quantity">
					<c:forEach var="i" begin="1" end="10">
						<option value="${i}">${i}</option>
					</c:forEach>
				</select>
			</td>
			<td><input type="submit" value="Add" /></td>
		</tr>
	</table>
	<input type="hidden" name="productid"
		value="${product.id}" />
</form>


