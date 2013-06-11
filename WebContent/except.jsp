<%@page contentType="text/html" isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head><title>Errorpage</title></head>
<body>

<c:if test="${sessionScope.debug == null}">
I am very sorry but an Exception has occurred: ${pageContext.exception.message}
</c:if>

<c:if test="${sessionScope.debug != null}">
	<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
		<p>${trace}</p>
	</c:forEach>
</c:if>

<p>
<FORM><INPUT Type="button" VALUE="Back" onClick="history.go(-1);return true;"></FORM>



</body>
</html>
