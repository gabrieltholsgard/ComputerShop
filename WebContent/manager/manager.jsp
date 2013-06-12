<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link
	rel="stylesheet"
	href="./css/styles.css"
	type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manager Page</title>
<body>
	<h1 align="center">Welcome Manager: <i>${sessionScope.currentUser}</i></h1>
	<table align="center">
		<tr>
			<td><h2>Main Menu</h2></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<form action="manager?action=manage" method="POST">
					<input type="submit" value="Manage products">
					<input type="hidden" name="type" value="product">
				</form>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<form action="manager?action=manage" method="POST">
					<input type="submit" value="Manage components">
					<input type="hidden" name="type" value="components">
				</form>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<form action="manager?action=addProduct" method="POST">
					<input type="submit" value="Add new product">
				</form>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<form action="manager?action=addComponent" method="POST">
					<input type="submit" value="Add new component">
				</form>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<form action="manager?action=orders" method="POST">
					<input type="submit" value="View Orders">
				</form>
			</td>
		</tr>
		<c:if test="${sessionScope.currentUser != null}">
			<tr></tr>
			<tr></tr>
			<tr>
				<td>
					<form action="manager?action=profile" method="POST">
    					<input type="submit" value="Update Profile" />
  					</form>
  				</tr>
  			<tr>
  			<tr></tr>
			<tr></tr>
			<tr>
				<td>
					<form action="manager?action=usercreate" method="POST">
    					<input type="submit" value="New Profile" />
  					</form>
  				</tr>
  			<tr>
  			<tr></tr>
			<tr></tr>
			<tr>
				<td>
  					<form action="manager?action=logout" method="POST">
	    				<input type="submit" value="Logout" />
  					</form>
  				</td>
  			</tr>
  			<tr>
				<td>
  					<form action="manager?action=showusers" method="POST">
	    				<input type="submit" value="Show Users" />
  					</form>
  				</td>
  			</tr>
		</c:if>
		<c:if test="${sessionScope.currentUser == null}">
			<tr></tr>
			<tr></tr>
			<tr>
				<td>
					<form action="manager" method="POST">
						<input type="submit" value="Login" />
					</form>
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>

