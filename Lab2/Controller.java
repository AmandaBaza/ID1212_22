import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Controller {
    public static void main(String[] args) {
        int port = 8080;
        ServerSocket server;
        int cookieId = 0;

        try {
            server = new ServerSocket(port);

            while (true){
                Socket player = server.accept();
                Model gameSession = new Model(player); //cookie?
                //gameSession.start();

                System.out.println("New player connected!");

                BufferedReader request = new BufferedReader(new InputStreamReader(player.getInputStream()));

                String data = request.readLine();
                while (data != null){
                    //if (data.contains()){
                        System.out.println(data);
                    //}
                    data = request.readLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
