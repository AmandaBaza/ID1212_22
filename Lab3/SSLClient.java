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


        //Sätter upp koppling till server
        Socket socket = new Socket(server, port);
        Writer outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        makeRequests("EHLO " + server , outputStreamWriter, in);
        makeRequests("STARTTLS", outputStreamWriter, in);


        //Läser igenom anropen
        String msg = "";
        while (msg != null) {
            msg = in.readLine();
            System.out.println(msg);
            if(msg.contains("220 2.0.0")){
                break;
            }
        }


        //Skapar en säker koppling
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket(socket, socket.getInetAddress().getHostAddress(), port, true);

        Writer sslOutputStreamWriter = new OutputStreamWriter(sslSocket.getOutputStream());
        BufferedReader sslIn = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        //Läser in användarnamn
        System.out.println("Enter KTH username: ");
        String username = System.console().readLine();


        //Krypeterar användarnamn i Base 64
        String encryptedUsername = Base64.getEncoder().encodeToString(username.getBytes());

        //Läser in lösenord
        System.out.println("Write your password: ");
        String password = String.valueOf(System.console().readPassword());

        //Krypterar lösenord i Base 64
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        //Läser in mottagare
        System.out.println("Enter receiver: ");
        String receiver = System.console().readLine();

        //Initiera konversation och authentisera inloggning
        makeRequests("EHLO " + server, sslOutputStreamWriter, sslIn);

        makeRequests("AUTH LOGIN", sslOutputStreamWriter, sslIn);
        makeRequests(encryptedUsername, sslOutputStreamWriter, sslIn);
        makeRequests(encryptedPassword, sslOutputStreamWriter, sslIn);


        while ((msg=sslIn.readLine()) != null) {
            System.out.println(msg);

            if (msg.contains("235 2.7.0")) {
                makeRequests("MAIL FROM:" + "<" + username + "@kth.se>", sslOutputStreamWriter, sslIn);
                makeRequests("RCPT TO:" + "<" + receiver + ">", sslOutputStreamWriter, sslIn);
                makeRequests("DATA", sslOutputStreamWriter, sslIn);
                writeMessage("Date: " + LocalTime.now(), sslOutputStreamWriter);
                writeMessage("From: " + "<" + username + "@kth.se>", sslOutputStreamWriter);
                writeMessage("Subject: Testytime", sslOutputStreamWriter);
                writeMessage("To: " + receiver, sslOutputStreamWriter);
                writeMessage("Email testing time!", sslOutputStreamWriter);
                writeMessage(".", sslOutputStreamWriter);
                makeRequests("QUIT", sslOutputStreamWriter, sslIn);

            }
        }


        socket.close();
        outputStreamWriter.close();
        in.close();

        sslOutputStreamWriter.close();
        sslSocket.close();
        sslIn.close();

        //En session betsår utav:
        //val av assymmetrisk nyckel
        //val av symmestrisk nyckel
        //en hash algoritm
        //(handskakningen skiljer åt lite mellan SSL och TLS)

    }
    
    private static void makeRequests(String message, Writer out, BufferedReader in){
        try {
            out.write(message);
            out.write("\r\n");
            out.flush();
            System.out.println(message);

            System.out.println(in.readLine());

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
