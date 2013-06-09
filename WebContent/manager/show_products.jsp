<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, tags.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="bookshop" uri="/bookshop"%>

<html>
<head><title>ComputerShop::Shop</title></head>
<body>
<h2>Computer Shop - Products</h2>
<jsp:useBean id="productList" class="beans.CompleteProductListBean" scope="application">
    Error, the bean should have been created in the servlet!
</jsp:useBean>


<c:set var="manager_productlist_xslt">
   <c:import url="manager_productlist_xslt.xsl"/>
</c:set> 

<x:transform xslt="${manager_productlist_xslt}">
    <jsp:getProperty name="productList" property="xml"/>
</x:transform>

<form action=manager?action=addProduct method=post>
	<input type="submit" value="Add new product">
</form>

<form action="manager" method="post">
	<input type="submit" value="Main meny">
</form>


</body>
</html>
