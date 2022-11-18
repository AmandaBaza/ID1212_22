import java.io.*;
import java.net.Socket;

public class MultiThread extends Thread{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;

    public MultiThread (Socket socket){

        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run(){



            try {

                //printWriter = new PrintWriter(socket.getOutputStream(), true);

                while (true) {

                    String in = bufferedReader.readLine();
                    System.out.println("MultiThread: " + in);
                    /* //code not working
                    this.bufferedWriter.write(in);
                    this.bufferedWriter.flush();
                     */
                }
            }
            catch (Exception e) {
                System.out.println(e);
                try {
                    socket.close();
                    bufferedWriter.close();
                    bufferedReader.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

    }

    public BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }
    public BufferedWriter getBufferedWriter() {
        return this.bufferedWriter;
    }
}
