<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ComputerShop::Component</title>
</head>
<body>

<h2>Add Component</h2>

<form action="manager?action=addComponent" method="POST">
<table>
	<tr>
		<td>
			<strong>Manufacturer</strong>
		</td>
		<td>
			<input type="text" name="manufacturer">
		</td>
	</tr>
	<tr>
		<td>
			<strong>Type</strong>
		</td>
		<td>
			<input type="text" name="type">
		</td>
	</tr>
	<tr>
		<td>
			<strong>Price</strong>
		</td>
		<td>
			<input type="text" name="price">
		</td>
	</tr>
	<tr>
		<td>
			<strong>Quantity</strong>
		</td>
		<td>
			<input type="text" name="quantity">
		</td>
	</tr>
</table>
<input type="submit" value="Add" />
<input type="hidden" name="save" value="true" /> 
</form>


<form action="manager?action=manage" method="post">
	<input type="submit" value="Show Components" />
	<input type="hidden" name="type" value="components" />
</form>


<form action="manager" method="post">
	<input type="submit" value="Main Menu" />
</form>

</body>
</html>