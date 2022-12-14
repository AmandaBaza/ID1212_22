import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ChatServer {
    public static void main(String[]args){
        int port = 8080;


        //Set up Server Socket
        ServerSocket server = null;
        //Set up Socket
        Socket socket = null;
        //Start the input stream
        BufferedReader in = null;

        try{
            //Start server on port
            server = new ServerSocket(port);
            //Waits for a client to try to connect to the server,
            //accepts socket connection and gives back an instance of the Socket

        }catch (IOException err){
            System.out.println(err);
        }try {
            while (true){
                socket = server.accept();
                ClientHandler multiThread = new ClientHandler(socket);
                multiThread.start();

                System.out.println("New client connected!");
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Error from Server");
        }
    }
}
