<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Read users</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>SURNAME</th>
        <th>EMAIL</th>
        <th>LOGIN</th>
        <th>PASSWORD</th>
        <th>USER ROLE</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstname}</td>
            <td>${user.surname}</td>
            <td>${user.email}</td>
            <td>${user.login}</td>
            <td>${user.password}</td>
            <td>${user.userRole}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>