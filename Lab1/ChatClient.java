import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        Socket socket;
        String address ="localhost";
        OutputStreamWriter out;
        try {
            socket = new Socket(address, port);
            out = new OutputStreamWriter(socket.getOutputStream());

            out.write("fabled");

            out.close();



            socket.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}