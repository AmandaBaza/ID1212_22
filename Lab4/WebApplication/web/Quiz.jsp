<a href="url"></a><%-- 
    Document   : Quiz
    Created on : 2023-jan-08, 17:09:01
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
        <form>
            <fieldset>
                
                <%
                   int id = ub.getQuizId(request.getParameter("quiz"));
                   
                   ub.updateResult(id);
                   
                   String[] quiz = ub.getQuestions(id).toArray(new String[0]);     
                    
                   for(int i = 0; i < (quiz.length); i+=3){

                       out.println("<legend>" + quiz[i] + "</legend>");
                       
                       String[] quest = quiz[i+1].split("/");
                       String[] ans = quiz[i+2].split("/");
                       
                       for(int j = 0; j < (quest.length); j++){
                            
                            out.println("<div>"
                                   + "<input type='checkbox' id='" + quest[j] + "' name='question' value='" + ans[j] +"q" + (i/3) + "q" + quest[j] + "' />"
                                   + "<label for='" + quest[j] +"'>" + quest[j] + "</label>"
                                   + "</div>");
                       }
                       
                   }
                %>
                <input type='submit' value='submit'>
            </fieldset>
        </form>
    </body>
</html>
