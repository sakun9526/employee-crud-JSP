<%-- 
    Document   : employee-form
    Created on : Aug 30, 2020, 6:12:38 PM
    Author     : Sakun
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee CRUD</title>
        <link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
    </head>
    <body>
        <header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="#" class="navbar-brand"> Employee Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Employees</a></li>
			</ul>
		</nav>
	</header>                               
       <br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${employees != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${employees == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${employees != null}">
                                                    Edit Employee
                                                </c:if>
						<c:if test="${employees == null}">
                                                    Add New Employee
                                                </c:if>
					</h2>
				</caption>

				<c:if test="${employees != null}">
					<input type="hidden" name="id" value="<c:out value='${employees.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Employee Name</label> <input type="text"
						value="<c:out value='${employees.name}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Employee Email</label> <input type="text"
						value="<c:out value='${employees.email}' />" class="form-control"
						name="email">
				</fieldset>

				<fieldset class="form-group">
					<label>Employee NIC</label> <input type="text"
						value="<c:out value='${employees.nic}' />" class="form-control"
						name="nic">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>                                 
    </body>
</html>
