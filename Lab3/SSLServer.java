import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class SSLServer {
    //börjar med krypterad session TODO make try catch
    public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, IOException {
        int port = 993;
        String server = "webmail.kth.se";
        String protocol = "SSL";
        String username = "abaz";


        //create Socket
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket(server, port);

        //connect
        Writer outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //To read password
        String password = String.valueOf(System.console().readPassword());

        //Login
        outputStreamWriter.write("a001 LOGIN "+username+" " + password); //TODO
        //outputStreamWriter.write();
        outputStreamWriter.flush();

        KeyStore ks = null;
        ks = KeyStore.getInstance(protocol);
        //InputStream in = null;
        //in = new FileInputStream(new File());



        //ks.load();

        SSLContext ctx = SSLContext.getInstance("TLS");



    }
}
