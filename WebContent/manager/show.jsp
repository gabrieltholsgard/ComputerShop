<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, tags.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="bookshop" uri="/bookshop"%>

<html>
<head><title>ComputerShop::Shop</title></head>
<body>
<h2>Order Items - Computer Shop</h2>
<jsp:useBean id="bookList" class="beans.BookListBean" scope="request">
    Error, the bean should have been created in the servlet!
</jsp:useBean>


<c:set var="productlist_xslt">
   <c:import url="productlist_xslt.xsl"/>
</c:set> 

<x:transform xslt="${productlist_xslt}">
    <jsp:getProperty name="bookList" property="xml"/>
</x:transform>


<FORM><INPUT Type="button" VALUE="Back" onClick="history.go(-1);return true;"></FORM>
</body>
</html>
