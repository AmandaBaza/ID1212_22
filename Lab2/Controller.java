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
        int port = 8000;
        ServerSocket server;

        try {
            server = new ServerSocket(port);
            while (true){

                Socket socket = server.accept();
                //setCookie();
                System.out.println("New connection!");

                Runnable runnable =  new Controller(socket);
                Thread thread = new Thread(runnable);
                thread.start();
/**
 * Socket player = server.accept();
 *                 Model gameSession = new Model(player); //cookie?
 *                 //gameSession.start();
 *
 *                 System.out.println("New player connected!");
 *
 *                 BufferedReader request = new BufferedReader(new InputStreamReader(player.getInputStream()));
 *
 *                 String data = request.readLine();
 *                 while (data != null){
 *                     //if (data.contains()){
 *                         System.out.println(data);
 *                     //}
 *                     data = request.readLine();
 *                     View.response(gameSession, player);
 *                 }
 */
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
        }  else if (data.contains("GET /favicon.ico HTTP/1.1"))
        {
            System.out.println("Another request for probably favicon is done!");
            System.out.println("--------------------------");
            System.out.println(data);
            System.out.println("--------------------------");
            // Fix View.favicon response

        } else if(data.contains("GET")) {
            System.out.println("Made it to Controller! ... ");
            gameSession.newGuess(0);
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
                    System.out.println("----------COOKIE"+cookie+"------------");
                }
                dataSB.append(data).append("\n");
                data = request.readLine();
            }
            data = dataSB.toString();
            System.out.println("----------------------");
            System.out.println(data);
            System.out.println("----------------------");
            if (!cookie.equals("")){
                System.out.println("COOKIE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                for (Model session: allPlayers) {
                    System.out.println("Player: "+session.cookie+"--------------");
                    if(cookie.equals(session.cookie)){
                        System.out.println("Found !!");
                        session.socket = player;
                        handleInput(data, session);
                        //break;
                    }
                }
            }else {
                Model gameSession = new Model(this.player, cookieId);
                cookieId++;
                System.out.println(cookieId+" NO COOKIE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("New Model instans made!!!!!!!!!!!!!!!!!!!!!!");
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
