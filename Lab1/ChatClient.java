import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        Socket socket;
        String address ="localhost"; //hej
        //OutputStreamWriter out;
        PrintWriter out;
        try {

            InputStreamReader i = new InputStreamReader(System.in);
            BufferedReader b = new BufferedReader(i);
            /*System.out.println("Enter Course");
            String in = b.readLine();
            System.out.println(in);*/
            socket = new Socket(address, port);
            //out = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(socket.getOutputStream(), true);


            //out.write("in");
            //out.close();



            socket.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}