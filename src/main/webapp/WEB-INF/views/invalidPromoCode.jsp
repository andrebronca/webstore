<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>Invalid Promo Code</title>
	</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1 class="alert alert-danger">Invalid Promo Code</h1>
			</div>
		</div>
	</section>
	
	<section>
		<div class="container">
			<p>
				<a href='<s:url value="/products" />' class="btn btn-primary">
					<span class="glyphicon glyphicon-hand-left"></span>
				</a>
			</p>
		</div>
	</section>
</body>
</html>