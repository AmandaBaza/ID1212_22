<%-- 
    Document   : Answears
    Created on : 2023-jan-09, 01:50:56
    Author     : Dante
--%>

<jsp:useBean class="L4.UserBean" id="ub" scope="session"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String[] resp = request.getParameterValues("question");
            int score = 0;
            for(int i = 0; i < resp.length; i++){
                String[] result = resp[i].split("q");
                
                if(result[0].equals("0")){
                    score--;
                    out.print("<p>Answear to question "+ result[1] + ": " + result[2] + " is incorrect</p>");
                }
                else{
                    score++;
                    out.print("<p>Answear to question "+ result[1] + ": " + result[2] + " is correct!</p>");
                }
                
            }
            out.println("<p>Your score is: "+score+"</p>");
            ub.updateScore(score);
            

        %>
        
        <!--<a href="/WebApplication/Test-jsp.jsp">New Game</a>-->
        <!--  <form><input type='submit' value='NewGame' name='gogo'/></form>-->
    </body>
</html>
