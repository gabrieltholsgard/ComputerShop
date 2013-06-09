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


