<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ComputerShop::Shop</title>
</head>
<body>

<h2>Add Product</h2>

<form action="manager?action=addProduct" method="post">
<table>
		<tr>
			<td>
				<strong>Product:</strong>
			</td>
			<td>
				<input type="text" name="product" />
			</td>
		</tr>
		<tr>
			<td>
				<strong>Profit</strong>
			</td>
			<td>
				<input type="text" name="profit" />
			</td>
		</tr>
		<tr>
			<td>
				<strong>Description</strong>
			</td>
			<td>
				<input type="text" name="description" />
			</td>
		</tr>
</table>

<input type="submit" value="Save" />
<input type="hidden" name="save" value="true" />
</form>

<form action="manager" method="post">
	<input type="submit" value="Main Meny" />
</form>

</body>
</html>