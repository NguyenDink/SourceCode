<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>Murach's Java Servlets and JSP</title>
	<link rel="stylesheet" href="styles/main.css" type="text/css">
</head>

<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--	<c:import url="/includes/header.html" /> -->
	<%@ include file="/includes/header.html" %>
	
	<h1>Join our email list</h1> 
	<h3>Exercise 5-1 & 6-1 & 8-1</h3>
	<p>To join our email list, enter your name and email address below.</p>
	<p><i>${message}</i></p>
	<jsp:useBean id="user" scope="session" class="models.User"/>
	<form action="emailList" method="post">
		<input type="hidden" name="action" value="add">

		<label class="pad_top">Email:</label>
		<input type="email" name="email" value="${user.email}"><br>

		<label class="pad_top">First Name:</label>
		<input type="text" name="firstName" value="${user.firstName}"><br>

		<label class="pad_top">Last Name:</label>
		<input type="text" name="lastName" value="${user.lastName}"><br>

		<label>&nbsp;</label>
		<input type="submit" value="Join Now" id="submit"><br>
	</form>
	
<!--	<c:import url="/includes/footer.jsp" /> -->
	<%@ include file="/includes/footer.jsp" %>
	<hr>
	
	<h3>Exercise 5-2</h3>
	<p><b>Post Method</b></p>
	<form action="test" method="post">
		<label>&nbsp;</label>
		<input type="submit" value="Join Now" id="submit">
	</form>
	<p><b>Get Method</b></p>
	<p>Enter the /test URL into the browser to run the test servlet. This should show that the test servlet works for the HTTP GET method.</p>
</body>

</html>