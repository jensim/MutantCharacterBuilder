
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	</head>
	<body>
		<%
			String scheme = request.getScheme();
			String serverName = request.getServerName();
			int serverPort = request.getServerPort();
			String uri = request.getRequestURI();
			
			/*
			String prmStr = null;
			Map paramMap = request.getParameterMap();
			for (Object key : paramMap.keySet()) {
				if (prmStr == null) {
					prmStr = "?" + key.toString() + "=" + paramMap.get(key);
				} else {
					prmStr += "&" + key.toString() + "=" + paramMap.get(key);
				}
			}*/

			String url = "?redirect_url=" + scheme + "://" + serverName + (serverPort != 80 ? (":" + serverPort) : "" ) + uri + "chargen/";
		%>
		<h1>Login providers</h1>
		<p><a href="https://github.com/login/oauth/authorize<%= url %>&client_id=9e81409afda6cd5c3161&state=github">Github</a></p>
		<p><a href="https://accounts.google.com/o/oauth2/auth">Google</a></p>
		<p><a href="https://www.facebook.com/dialog/oauth">Facebook</a></p>
		<p><a href="https://stackexchange.com/oauth">Stackexchange</a></p>

	</body>
</html>