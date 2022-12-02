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
                Integer guess = null;
                //gameSession.start();

                System.out.println("New player connected!");

                BufferedReader request = new BufferedReader(new InputStreamReader(player.getInputStream()));

                String data = request.readLine();

                while (data != null){


                    System.out.println(data);
                    data = request.readLine();
                    guess = getGuess(data);
                    System.out.println(guess);
                    gameSession.newGuess(guess);


                    //View.response(gameSession, player);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getGuess(String request){
        String[] split = request.split("\\s");
        String guess = "";
        //if (split[0].equals("GET")) {
            for (int i = 0; i < split[1].length(); i++) {

                if (Character.isDigit(split[1].charAt(i))) {
                    guess += split[1].charAt(i);
                }
            //}
        }
            System.out.println(guess);
        return Integer.valueOf(guess);
    }

}
