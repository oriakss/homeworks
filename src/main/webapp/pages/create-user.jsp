<%@ page import="ru.clevertec.entity.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create user</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>FIRSTNAME</th>
        <th>SURNAME</th>
        <th>EMAIL</th>
        <th>LOGIN</th>
        <th>PASSWORD</th>
        <th>USER ROLE</th>
    </tr>
    </thead>
    <tbody>
    <form action="/users/create" method="post">
        <tr>
            <td><input type="text" name="firstname" placeholder="put your firstname" required></td>
            <td><input type="text" name="surname" placeholder="put your surname" required></td>
            <td><input type="email" name="email" placeholder="put your email" required></td>
            <td><input type="text" name="login" placeholder="put your login" required></td>
            <td><input type="password" name="password" placeholder="put your password" required></td>
            <td><select name="userRole" required>
                <option label="---put role---"></option>
                <c:forEach var="role" items="${UserRole.values()}">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
            </td>
            <td><input type="submit" value="Create"></td>
        </tr>
    </form>
    </tbody>
</table>
</body>
</html>