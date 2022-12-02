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
                //Integer guess = null;
                //gameSession.start();

                System.out.println("New player connected!");

                BufferedReader request = new BufferedReader(new InputStreamReader(player.getInputStream()));

                String data = request.readLine();
                /*String guessLine = data;
                guess = getGuess(guessLine);*/

                while (request.ready()){


                    handleInput(data, gameSession);
                    /*if(data.contains(("GET /?guess="))){
                        guess = getGuess(data);
                        gameSession.newGuess(guess);
                    }
                    else if(data.contains("GET")){
                        System.out.println("Made it to Controller!");
                        gameSession.newGuess(null);
                    }*/

                    System.out.println(data);


                    //View.response(gameSession, player);
                    data = request.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getGuess(String data){
        String[] split = data.split("\\s");
        String guess = "";

        //if (split[0].equals("GET")) {
            for (int i = 0; i < split[1].length(); i++) {

                if (Character.isDigit(split[1].charAt(i))) {
                    guess += split[1].charAt(i);
                }
            //}
        }
            System.out.println(guess);
        return Integer.parseInt(guess);
    }

    public static void handleInput(String data, Model gameSession){
        if(data.contains(("GET /?guess="))){
            gameSession.newGuess(getGuess(data));
        }
        else if(data.contains("GET")){
            System.out.println("Made it to Controller!");
            gameSession.newGuess(null);
        }
    }

}
