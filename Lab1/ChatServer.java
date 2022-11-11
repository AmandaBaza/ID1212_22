import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;

public class ChatServer {
    public static void main(String[]args){
        int port = 8080;


        //Set up Server Socket
        ServerSocket server = null;
        //Set up Socket
        Socket socket = null;
        //Start the input stream
        InputStreamReader in = null;

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
                MultiThread multiThread = new MultiThread(socket);
                multiThread.start();

                in = new InputStreamReader(socket.getInputStream());
                System.out.println("Start:");
                while (in.ready()){
                    System.out.print((char)in.read());
                }

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
