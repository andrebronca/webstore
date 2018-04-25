<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>ProductNotFound</title>
	</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1 class="alert alert-danger">
					<spring:message code="ProductNotFound.view.invalid.id" />${ invalidProductId }</h1>
				<p>All the available products in our store</p>
			</div>
		</div>
	</section>
	
	<section>
		<div class="container">
			<p>${ url }</p>
			<p>${ exception }</p>
		</div>
		
		<div class="container">
			<p>
				<a  href='<spring:url value="/products" /> ' class="btn btn-primary">
					<span class="glyphicon-hand-left glyphicon"></span>
					Products
				</a>
			</p>		
		</div>
	</section>
</body>
</html>