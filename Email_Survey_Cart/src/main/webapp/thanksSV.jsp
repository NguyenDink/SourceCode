<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Murach's Java Servlets and JSP</title>
	<link rel="stylesheet" href="styles/mainSV.css" type="text/css"/>
</head>
<body>
	<header>
		<h1>Thanks for taking our survey!</h1>
	</header>
	
	<section>
		<p>Here is the information that you entered: </p>		
		<label class="info-user"><b>Email:</b></label>
		<span>${user.email}</span><br>	
		<label class="info-user"><b>First Name:</b></label>
		<span>${user.firstName}</span><br>
		<label class="info-user"><b>Last Name:</b></label>
		<span>${user.lastName}</span><br>	
		<label class="info-user"><b>Date of Birth:</b></label>
		<span>${user.dateOfBirth}</span><br>	
		<label class="info-user"><b>Heard From:</b></label>
		<span>${user.hear}</span><br>
		<label class="info-user"><b>Like That:</b></label>
		<span>${user.cbLike}</span><br>
		<label class="info-user"><b>Updates:</b></label>
		<span>${user.cbSend}</span><br>
		
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	    <c:if test="${user.cbSend == 'Yes'}">
	        <label><b>Contact Via:</b></label>
	        <span>${user.contact}</span>
	    </c:if>
	
		<p>To fill out another survey, click the Back button browser or Back button shown below.</p>
		<form action="" method="post">
			<input type="hidden" name="action" value="join"> 
			<input type="submit" value="Back" id="submit">
		</form>
	</section>
</body>
</html>