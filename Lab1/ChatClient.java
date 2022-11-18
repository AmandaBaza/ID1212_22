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
            BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try {
                while (true){
                    if(receive.ready()){
                        System.out.println(receive.readLine());
                    }
                    out.write(in.read());
                    out.flush();
                }
            }catch(Exception e){
                System.out.println("Lost Connection");
            }

            out.close();
            socket.close();
        }catch (Exception e){

            if (socket != null) { socket.close(); }
            System.out.println("Couldn't Connect");
        }
    }
}