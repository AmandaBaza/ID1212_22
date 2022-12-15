import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class SSLClient {
    //går över till kryptering under sessionen
    public static void main(String[] args) throws IOException {
        int port = 587;
        String server = "smtp.kth.se";
        String protocol = "STARTTLC";

        System.out.println("Enter KTH username: ");
        String username = System.console().readLine();

        //create Socket
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket(server, port);

        //connect
        Writer outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //To read password
        System.out.println("Write your password: ");
        String password = String.valueOf(System.console().readPassword());

       /*
        //Login
        outputStreamWriter.write("a001 LOGIN " //a001 is the tag
                + username + " " + password);
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();
        */

        //En session betsår utav:
        //val av assymmetrisk nyckel
        //val av symmestrisk nyckel
        //en hash algoritm
        //(handskakningen skiljer åt lite mellan SSL och TLS)
    }
}
