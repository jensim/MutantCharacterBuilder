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
		
		<style>
			body{
				color:#000030;
				background:#f3f3ff;
			}
			
		</style>
    </head>
    <body>
        <h1>Register account!</h1>
		<form action="register_processed.jsp" method="POST">
			email:&nbsp;<input type="text" name="email" /><br/>
			passw:&nbsp;<input type="text" name="pass" /><br/>
			<input type="submit" value="Request" />
		</form>
    </body>
</html>
