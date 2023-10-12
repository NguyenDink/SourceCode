<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	
	<h1>Thanks for joining our email list</h1>
	<p>Here is the information that you entered:</p>

	<label><b>Email:</b></label>
	<span>${user.email}</span><br>

	<label><b>First Name:</b></label>
	<span>${user.firstName}</span><br>

	<label><b>Last Name:</b></label>
	<span>${user.lastName}</span><br>

	<p>This email address was added to our list on ${requestScope.currentDate}</p>

    <p>The first address on our list is ${sessionScope.users[0].email}<br>
       The second address on our list is ${sessionScope.users[1].email}
    </p>
      
    <p>For customer service, contact ${initParam.custServEmail}</p>  
	
	<p>To enter another email address, click on the Back button in your
		browser or the Return button shown below.</p>
	<form action="" method="post">
		<input type="hidden" name="action" value="join"> 
		<input type="submit" value="Return">
	</form>
	
<!--	<c:import url="/includes/footer.jsp" /> -->
	<%@ include file="/includes/footer.jsp" %>
</body>

</html>