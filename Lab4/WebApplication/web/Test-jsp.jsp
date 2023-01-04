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
        Hello <%=ub.getUsername()%>(<%=ub.getPassword()%>)!<br/>
        Your Results:<br/> <%
            for (int i = 0; i < ub.userResults().size(); i++) {
                out.println("Subject "+ub.userResults().get(i).getSubject()+ "<br/>");
                out.println("Score: "+ub.userResults().get(i).getScore()+ "<br/>");
            }      
        %>
        <br/>All Quizzes:<br/> <%
            for (int i = 0; i < ub.getAllQuizzes().size(); i++) {
                out.println("<button>"+ ub.getAllQuizzes().get(i)+"</button><br/>");
            }      
        %>
    </body>
</html>
