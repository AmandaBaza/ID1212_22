import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class View {
    Socket player;
    OutputStream outputStream;
    public View (Socket player) {
        try {
            this.player = player;
            this.outputStream = player.getOutputStream();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void response(Model player) {
        String body = "";
        if(player.numberOfGuesses == 0){
            body = getBody("Welcome to the Number Guess Game. Guess a number between 1 and 100!");
        } else if(player.win){
            System.out.println("Made it to View for Win!");
            body = getBody("You win!");
        } else if(player.guessHigher){
            System.out.println("Made it to View higher!");
            body = getBody("Nope, higher!");
        }else{
            System.out.println("Made it to View for lower!");
            body = getBody("Nope, lower!");
        }
        try {
            StringBuilder responseString = new StringBuilder();
            PrintStream response = new PrintStream(player.socket.getOutputStream());
            responseString.append("HTTP/1.1 200 OK\n");
            responseString.append("Content-Type: text/html; charset=utf-8\n");
            responseString.append("Set-Cookie: ");
            responseString.append(player.cookie);

            responseString.append(body);         //message

            response.print(responseString);
            //close socket
            player.socket.close();

        } catch (Exception e) {
            System.out.println("ERROR: "+ e.getMessage());
        }
    }

    private static String getBody(String title){
        String block = "<link rel=\"shortcut icon\" href=\"about:blank\">";
        String body = "\r\n\r\n<html><head>"+block+"</head><body><h1>"
                + "<link rel=\"icon\" href=\"data:,\">"
                + "<h1>" + title + "</h1>"
                + "<form name=\"guessForm\">"
                + "<input type=\"text\" name=\"guess\"><br>"
                + "<input type=\"submit\" value=\"guessedValue\"><br>"
                + "</form>"
                + "</body></html>\r\n\r\n";
        return body;
    }
}
