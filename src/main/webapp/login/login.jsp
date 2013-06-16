<%-- 
    Document   : login-visibleform
    Created on : Jun 6, 2013, 11:49:50 AM
    Author     : jens
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login</title>
    </head>
    <body bgcolor="f3f3ff">
        <h1>Hello World!</h1>
		<form method="post" action="j_security_check" >
		email:&nbsp;<input type="text" name="j_username" /><br/>
		password:&nbsp;<input type="password" name="j_password" /> <br/>
		<input type="submit"  /><br/>
		<a href="../login/register.jsp">Register</a>
	</form>
    </body>
</html>
