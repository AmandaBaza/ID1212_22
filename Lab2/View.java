import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class View {
    Socket player;
    OutputStream outputStream;
    //int length = 0;
    //String ContentLength = "Content-Length: "+length+"\r\n";
    public View (Socket player) {
        try {
            this.player = player;
            this.outputStream = player.getOutputStream();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void response(Model player, Socket socket) {
        String body = "";
        if(player.numberOfGuesses == 0){
            body = getBody("Welcome to the Number Guess Game. Guess a number between 1 and 100!");
        } else if(player.win){
            body = getBody("You win!");
        } else if(player.guessHigher){
            body = getBody("Nope, higher!");
        }else{
            body = getBody("Nope, lower!");
        }
        try {
            PrintStream response = new PrintStream(socket.getOutputStream());

            response.println("HTTP/1.1 200 OK");
            response.println("Content-Type: text/html");
            response.println("<link rel=\"icon\" href=\"data:\" />"); //should ignore/fake favicon
            response.println("Content-Length: ");
            response.println("\r\n");       //empty line TODO-change to print? Extra line?
            response.println(body);         //message
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getBody(String title){
        String body = "\r\n\r\n<html><body><h1>"
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
