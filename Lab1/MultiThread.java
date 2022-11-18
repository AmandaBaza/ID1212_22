import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThread extends Thread{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private static ArrayList <MultiThread> allClients = new ArrayList<>();

    public MultiThread (Socket socket){

        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            allClients.add(this);
        } catch (Exception e) {
            System.out.println("MThread-K");
            throw new RuntimeException(e);

        }

    }

    @Override
    public void run(){

        while (socket.isConnected()){

            try {
                while (this.bufferedReader.ready()){
                    String in = this.bufferedReader.readLine();
                    System.out.println("Message received: " + in);
                    broadcast(this.socket, in);
                }
            } catch (Exception e) {
                System.out.println(e);
                try {
                    socket.close();
                    bufferedWriter.close();
                    bufferedReader.close();
                    break;

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void broadcast(Socket sender, String message){
        if(!message.equals("")){
            try{
                for (MultiThread client: allClients) {
                    if(!client.socket.equals(sender)){
                        client.bufferedWriter.write("Message received: "+ message);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
                }
            }catch(Exception e){
                System.out.println("Error in when broadcasting messages");
            }
        }
    }
}
