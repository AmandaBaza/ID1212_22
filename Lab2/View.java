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
        System.out.println("Made it to View!");
        if(player.numberOfGuesses == 0){
            System.out.println("Made it to View for new game!");
            body = getBody("Welcome to the Number Guess Game. Guess a number between 1 and 100!");
        } else if(player.win){
            body = getBody("You win!");
        } else if(player.guessHigher){
            body = getBody("Nope, higher!");
        }else{
            body = getBody("Nope, lower!");
        }
        try {
            StringBuilder responseString = new StringBuilder();
            PrintStream response = new PrintStream(socket.getOutputStream());

            responseString.append("HTTP/1.1 200 OK\n");
            responseString.append("Content-Type: text/html\n");
            //responseString.append("<link rel=\"icon\" href=\"data:\" />\n"); //should ignore/fake favicon
            responseString.append("Content-Length: ");
            responseString.append(body.length());    //empty line TODO-change to print? Extra line?
            responseString.append(body);         //message
            System.out.println(responseString);
            response.print(responseString);
        } catch (Exception e) {
            System.out.println("ERROR: "+ e.getMessage());
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
                + "<p>Http</p>"
                + "</body></html>\r\n\r\n";
        return body;
    }
}
