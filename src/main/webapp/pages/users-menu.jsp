<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.clevertec.entity.UserRole" %>
<html>
<head>
    <title>USERS MENU</title>
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
        <form method="post">
            <tr>
                <td><input type="text" name="id" value="${user.id}" readonly size="1"></td>
                <td><input type="text" name="firstname" value="${user.firstname}" required></td>
                <td><input type="text" name="surname" value="${user.surname}" required></td>
                <td><input type="email" name="email" value="${user.email}" required></td>
                <td><input type="text" name="login" value="${user.login}" required></td>
                <td><input type="text" name="password" value="${user.password}" required></td>
                <td>
                    <select name="userRole" required>
                        <option>${user.userRole}</option>
                        <c:forEach var="role" items="${UserRole.values()}">
                            <option>${role}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" formaction="/users/delete" value="Delete"></td>
                <td><input type="submit" formaction="/users/update" value="Update"></td>
            </tr>
        </form>
    </c:forEach>
    </tbody>
</table>
<form method="get">
    <button formaction="/users/create">Create user</button>
    <button formaction="/users/read">Get user list</button>
    <div><br><a href="/admin-menu">Go to ADMIN MENU</a></div>
</form>
</body>
</html>