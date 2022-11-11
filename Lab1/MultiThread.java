import java.io.*;
import java.net.Socket;

public class MultiThread extends Thread{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public MultiThread (Socket socket){

        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run(){

        while (socket.isConnected()){

            try {

            }
            catch (Exception e) {
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
}
