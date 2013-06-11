<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="webstore" uri="/bookshop"%>

<html>
<head>
<title>ComputerShop::Password</title>
</head>
<body>
	<h1>
		Please update the profile for the user <i>${sessionScope.currentUser}</i>
	</h1>
	${(passwordInvalid != null)?(passwordInvalid):null}
	<form action="manager?action=profilechange" method="post">
		<table>
			<tr>
				<td>Username:</td>
				<td>
					<input type="text" name="user"
							value="${sessionScope.currentUser}" disabled>
				</td>
			</tr>
			<tr>
				<td>New password:</td>
				<td>
					<input type="password" name="password"
							value="${profile.password}">
				</td>
			</tr>
			<tr>
				<td>Verify password:</td>
				<td>
					<input type="password" name="password2"
							value="${profile.password}"></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td>
					<input type="text" name="name" value="${profile.name}">
				</td>
			</tr>
			<tr>
				<td>Street Address:</td>
				<td>
					<input type="text" name="street" value="${profile.street}">
				</td>
			</tr>
			<tr>
				<td>Zipcode:</td>
				<td>
					<input type="text" name="zip" value="${profile.zip}">
				</td>
			</tr>
			<tr>
				<td>City:</td>
				<td>
					<input type="text" name="city" value="${profile.city}">
				</td>
			</tr>
			<tr>
				<td>Country:</td>
				<td>
					<input type="text" name="country"
							value="${profile.country}">
				</td>
			</tr>
			<tr>
				<td>Select roles:</td>
				<c:forEach var="next" items="${roles}">
					<tr>
						<c:if test="${next.value == true}">
							<td>
								<input type="checkbox" name="${next.key}" checked>
								${next.key}
							</td>
						</c:if>
						<c:if test="${next.value == false}">
							<td>
								<input type="checkbox" name="${next.key}">
								${next.key}
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tr>
		</table>
		<input type="submit" value="Go">
	</form>

  
</body>
</html>
