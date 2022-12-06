import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;

public class Controller implements Runnable{
    private Socket player;
    private static Integer cookieId = 0;
    private static ArrayList<Model> allPlayers = new ArrayList<Model>();

    private Controller(Socket socket){
        this.player = socket;
    }

    public static void main(String[] args) {
        int port = 8080;
        ServerSocket server;

        try {
            server = new ServerSocket(port);
            while (true){

                Socket socket = server.accept();

                Runnable runnable =  new Controller(socket);
                Thread thread = new Thread(runnable);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getGuess(String data){
        String[] split = data.split("\\s");
        String guess = "";

        for (int i = 0; i < split[1].length(); i++) {
            if (Character.isDigit(split[1].charAt(i))) {
                guess += split[1].charAt(i);
            }
        }
        return Integer.parseInt(guess);
    }

    public static void handleInput(String data, Model gameSession){
        if(data.contains(("GET /?guess="))){
            gameSession.newGuess(getGuess(data));
        }  else if (data.contains("GET /favicon.ico HTTP/1.1"))
        {

        } else if(data.contains("GET")) {
            gameSession.newGuess(null);
        }
    }

    @Override
    public void run() {
        try{
            BufferedReader request = new BufferedReader(new InputStreamReader(this.player.getInputStream()));

            StringBuilder dataSB = new StringBuilder();
            String data = request.readLine();
                /*String guessLine = data;
                guess = getGuess(guessLine);*/
                String cookie  = "";

            while (!data.equals("")){
                if(data.contains("Cookie")){
                    String[] holder = data.split(" ");
                    cookie = holder[1];
                }
                dataSB.append(data).append("\n");
                data = request.readLine();
            }
            data = dataSB.toString();
            if (!cookie.equals("")){
                for (Model session: allPlayers) {
                    if(cookie.equals(session.cookie)){
                        session.socket = player;
                        handleInput(data, session);
                    }
                }
            }else {
                Model gameSession = new Model(this.player, cookieId);
                cookieId++;
                allPlayers.add(gameSession);
                for (Model se: allPlayers  ) {
                    System.out.println("Loop: "+ se.cookie);
                }
                handleInput(data, gameSession);
            }
        }catch (Exception e){

        }
    }
}
