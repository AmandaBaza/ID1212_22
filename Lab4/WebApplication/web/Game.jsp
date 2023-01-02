<jsp:useBean class="L4.Game" id="game" scope="session"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Guessing Game</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h2>Guessing Game</h2>
        <form method="GET" action="/WebApplication/Servlet">
        <% if(game.getWin()){ %>
        <h2>Correct, you win!</h2>
        <% }else if(game.getGuessHigher()){ %>
        <h2>Nope, guess higher</h2>
        <p>Guess a number between 1-100<input type="text" name="guess" >
        <p><input type="submit" value="Submit">
        <% }else{ %>
        <h2>Nope, guess lower</h2>
        <p>Guess a number between 1-100<input type="text" name="guess" >
        <p><input type="submit" value="Submit">
        <%} %>
        </form>
        Correct answer:<%=game.getCorrectNumber()%>
    </body>
</html>
