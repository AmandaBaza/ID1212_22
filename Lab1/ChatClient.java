import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        int port = 8000;
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
                    out.write(in.read());
                    out.flush();
                    while(receive.ready()){
                        System.out.println(receive.readLine());
                    }
                }
            }catch(Exception e){
                System.out.println("Lost Connection");
            }

            out.close();
            socket.close();
        }catch (Exception e){
            //if(out != null){ out.close(); }
            if (socket != null) { socket.close(); }
            System.out.println("Couldn't Connect");
        }
    }
}