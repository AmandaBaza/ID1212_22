import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class ChatClient implements Runnable{
    private BufferedReader receive;
    private Socket socket;

    public ChatClient(Socket socket){
        try{
            this.socket = socket;
            this.receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (Exception e){

        }
    }

    @Override
    public void run(){
        try{
            while(true){
                System.out.println(receive.readLine());
            }
        }catch (Exception e){
            System.out.println("Error trying to receive a message");
            try {
                receive.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        Socket socket = null;
        String address = "localhost";
        //OutputStreamWriter out = null;
        try {
            socket = new Socket(address, port);
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            DataInputStream in = new DataInputStream(System.in);

            System.out.println("Connected!");
            Runnable listener = new ChatClient(socket);
            Thread thread = new Thread(listener);
            thread.start();
            try {
                while (true){
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