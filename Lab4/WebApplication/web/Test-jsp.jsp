<%--
    Document   : Test-jsp
    Created on : 2022-dec-28, 16:19:04
    Author     : Amanda
--%>
<jsp:useBean class="L4.UserBean" id="ub" scope="session"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<h1>Welcome!</h1>
Hello <%=ub.getUsername()%>(<%=ub.getPassword()%>)!
</body>
</html>