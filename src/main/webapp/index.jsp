<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>LOGIN</th>
        <th>PASSWORD</th>
    </tr>
    </thead>
    <tbody>
    <form action="/login" method="get">
        <tr>
            <td><input type="text" name="login" placeholder="put your login" required></td>
            <td><input type="password" name="password" placeholder="put your password" required></td>
            <td><input type="submit" value="Log in"></td>
        </tr>
    </form>
    </tbody>
</table>
<form method="get">
    <a href="/registration">Registration</a>
</form>
</body>
</html>