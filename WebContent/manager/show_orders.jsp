<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, tags.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ComputerShop::Shop</title>
</head>
<body>

<h2>Computer Shop - Orders</h2>
<jsp:useBean id="orderlist" class="beans.OrderListBean" scope="application">
	Error, the bean should have been created in the servlet!
</jsp:useBean>

<c:set var="manager_oederlist_xslt">
	<c:import url="manager_oederlist_xslt.xsl" />
</c:set>


<x:transform xslt="${manager_oederlist_xslt}">
	<jsp:getProperty name="orderlist" property="xml" />
</x:transform>

<FORM><INPUT Type="button" VALUE="Back" onClick="history.go(-1);return true;"></FORM>

</body>
</html>