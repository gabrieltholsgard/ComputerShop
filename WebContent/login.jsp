<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page for the Computershop</title>
<body bgcolor="white">
<form method="POST" action='<%= response.encodeURL("j_security_check") %>' >
  <table border="0" cellspacing="5" align="center">
    <tr>
      <td colspan="2" bgcolor="#FFDC75"><h2>Log in to the Computershop</td>
    </tr>
    <tr><td colspan="2"></td></tr>
    <tr>
      <th align="right">Username:</th>
      <td align="left"><input type="text" name="j_username"></td>
    </tr>
    <tr>
      <th align="right">Password:</th>
      <td align="left"><input type="password" name="j_password"></td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="Log In"></td>
      <td align="left"><input type="reset"></td>
    </tr>
    <c:if test="${sessionScope.usertype != manager}">
	    <tr>
	     <td><a href="shop?action=newuser">I ain't got no account</a>      
	     </td>
	    </tr>
    </c:if>
  </table>

</form>
</body>
</html>
