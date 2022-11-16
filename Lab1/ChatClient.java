import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        Socket socket = null;
        String address ="localhost";
        //OutputStreamWriter out = null;
        try {
            socket = new Socket(address, port);

            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            DataInputStream in = new DataInputStream(System.in);

            while (true){
                out.write(in.read());
                out.flush();
            }

            //out.write("After");
            //out.close();
            //socket.close();
        }catch (Exception e){
            //if(out != null){ out.close(); }
            if (socket != null) { socket.close(); }
            System.out.println("Client disconnected");
        }
    }
}