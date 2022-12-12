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
        String name = "";

        //create Socket
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket(server, port);

        //connect
        Writer outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Login
        outputStreamWriter.write("a001 LOGIN"); //TODO
        //outputStreamWriter.write(username);
        outputStreamWriter.flush();



        //To read password - Ta bort?
        char[] password = System.console().readPassword();

        Writer out = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        out.write("Name: "+ name +"\r\n");
        out.write("Product-ID: 67X-89\r\n");
        out.write("Address: 1280 Deniston Blvd, NY NY 10003\r\n");
        out.write("Card number: 4000-1234-5678-9017\r\n");
        out.write("Expires: 08/05\r\n");
        out.flush();


        //En session betsår utav:
        //val av assymmetrisk nyckel
        //val av symmestrisk nyckel
        //en hash algoritm
        //(handskakningen skiljer åt lite mellan SSL och TLS)
    }
}
