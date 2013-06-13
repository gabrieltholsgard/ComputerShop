<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, tags.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="bookshop" uri="/bookshop"%>

<c:if test="${sessionScope.currentUser != null}">

<c:set var="shoppingcart_xslt">
   <c:import url="shoppingcart_checkout_xslt.xsl"/>
</c:set> 
<x:transform xslt="${shoppingcart_xslt}">
   <checkout>
	   <bookshop:shoppingcart/>
	   <name>${profile.name}</name>
	   <address>${profile.street}</address>
	   <zip>${profile.zip}</zip>
	   <city>${profile.city}</city>
   </checkout>
</x:transform>
</c:if>
<c:if test="${sessionScope.currentUser == null}">
<table border="0">
<tr>
<td bgcolor="silver">
<br>Login Successfull!</br>
</td>
</tr>
<tr>
<td bgcolor="#FFDC75">
<form action ="show.jsp"><input type="submit" value="Continue Shopping"></form>
</td>
</tr>
</c:if>


