import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.time.LocalTime;
import java.util.Base64;
import java.net.Socket;

public class SSLClient {
    //går över till kryptering under sessionen
    public static void main(String[] args) throws IOException {
        int port = 587;
        String server = "smtp.kth.se";
        //String protocol = "STARTTLC";


        Socket socket = new Socket(server, port);
        Writer outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        makeRequests("EHLO " + server , outputStreamWriter, in);
        makeRequests("STARTTLS", outputStreamWriter, in);

        /*outputStreamWriter.write("EHLO " + server);
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();

        outputStreamWriter.write("STARTTTLS");
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();*/


        String msg = "";
        while (msg != null) {
            msg = in.readLine();
            System.out.println(msg);
            if(msg.contains("220 2.0.0")){
                break;
            }
        }


        //create SSLSocket
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket(socket, socket.getInetAddress().getHostAddress(), port, true);

        //connect
        Writer sslOutputStreamWriter = new OutputStreamWriter(sslSocket.getOutputStream());
        BufferedReader sslIn = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        System.out.println("Enter KTH username: ");
        String username = System.console().readLine();


        //Encrypt username in Base 64
        String encryptedUsername = Base64.getEncoder().encodeToString(username.getBytes());

        //To read password
        System.out.println("Write your password: ");
        String password = String.valueOf(System.console().readPassword());

        //Encrypt password in Base64
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        System.out.println("Enter receiver: ");
        String receiver = System.console().readLine();

        makeRequests("EHLO " + server, sslOutputStreamWriter, sslIn);
        makeRequests("AUTH LOGIN", sslOutputStreamWriter, sslIn);

        makeRequests(encryptedUsername, sslOutputStreamWriter, sslIn);
        makeRequests(encryptedPassword, sslOutputStreamWriter, sslIn);

        msg = "";
        while ((msg=sslIn.readLine()) != null) {
            //msg = sslIn.readLine();
            System.out.println(msg);

            if (msg.contains("334")) {
                System.out.println(sslIn.readLine());
                System.out.println(sslIn.readLine());
                makeRequests("MAIL FROM:" + "<" + username + ">", sslOutputStreamWriter, sslIn);
                makeRequests("RCPT TO:" + "<" + receiver + ">", sslOutputStreamWriter, sslIn);
                makeRequests("DATA", sslOutputStreamWriter, sslIn);
                makeRequests("QUIT", sslOutputStreamWriter, sslIn);
                writeMessage("Date: " + LocalTime.now(), sslOutputStreamWriter);
                writeMessage("From: SMTP User " + "<" + username + ">", sslOutputStreamWriter);
                writeMessage("Subject: Testytime", sslOutputStreamWriter);
                writeMessage("To: " + receiver, sslOutputStreamWriter);
                //writeMessage("", sslOutputStreamWriter);
                writeMessage("Email testing time!", sslOutputStreamWriter);
                writeMessage(".", sslOutputStreamWriter);
                makeRequests("QUIT", sslOutputStreamWriter, sslIn);

                while ((msg=sslIn.readLine()) != null){
                    System.out.println(msg);
                }
            }
        }

        /*
        sslOutputStreamWriter.write("EHLO " + server);
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("AUTH LOGIN");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write(encryptedUsername);
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write(encryptedPassword);
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("MAIL FROM:" + "<" + username + ">");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("RCPT TO:" + "<" + receiver + ">");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("DATA");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("Date: " + LocalTime.now());
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("From: SMTP User " + "<" + username + ">");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("Subject: Testytime");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("To: " + receiver);
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();
         */



        /*sslOutputStreamWriter.write("");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();*/

        /*
        sslOutputStreamWriter.write("Email testing time!");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write(".");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();

        sslOutputStreamWriter.write("QUIT");
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();
        */

        sslOutputStreamWriter.close();
        sslSocket.close();



        /*

        //Login
        sslOutputStreamWriter.write("a001 LOGIN " //a001 is the tag
                + username + " " + password);
        sslOutputStreamWriter.write("\r\n");
        sslOutputStreamWriter.flush();


        //En session betsår utav:
        //val av assymmetrisk nyckel
        //val av symmestrisk nyckel
        //en hash algoritm
        //(handskakningen skiljer åt lite mellan SSL och TLS)
         */
    }

    private static void makeRequests(String message, Writer out, BufferedReader in){
        try {
            out.write(message);
            out.write("\r\n");
            out.flush();
            System.out.println(message);

            //System.out.println(in.readLine());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void writeMessage(String message, Writer out){
        try {
            out.write(message);
            out.write("\r\n");
            out.flush();
            System.out.println(message);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
