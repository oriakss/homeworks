<html>
<head>
    <title>Registration</title>
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
    </tr>
    </thead>
    <tbody>
    <form action="/registration" method="post">
        <tr>
            <td><input type="text" name="firstname" placeholder="put your firstname" required></td>
            <td><input type="text" name="surname" placeholder="put your surname" required></td>
            <td><input type="email" name="email" placeholder="put your email" required></td>
            <td><input type="text" name="login" placeholder="put your login" required></td>
            <td><input type="password" name="password" placeholder="put your password" required></td>
            <td><input type="hidden" name="userRole" value="USER"></td>
            <td><input type="submit" value="Register"></td>
        </tr>
    </form>
    </tbody>
</table>
</body>
</html>