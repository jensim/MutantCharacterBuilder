<%-- 
    Document   : register
    Created on : Jun 9, 2013, 6:47:37 PM
    Author     : jens
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
        <title>Register new account</title>
    </head>
    <body>
        <h1>Register account!</h1>
		<form action="register_processed.jsp" action="POST">
			email:&nbsp;<input type="text" name="email" />
			<input type="submit" value="Request" />
		</form>
    </body>
</html>
