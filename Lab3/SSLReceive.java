import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class SSLReceive {
    //börjar med krypterad session TODO make try catch
    public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, IOException {
        int port = 993;
        String server = "webmail.kth.se";
        String protocol = "SSL";

        System.out.println("Enter KTH username: ");
        String username = System.console().readLine();

        //create Socket
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket(server, port);

        //output and input streams
        Writer outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),
                "US-ASCII");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //To read password
        System.out.println("Write your password: ");
        String password = String.valueOf(System.console().readPassword());

        //Login
        outputStreamWriter.write("a001 LOGIN " //a001 is the tag
                + username + " " + password);
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();
        //String reply = readAll();

        readUntilTag(in, "a001");

        System.out.println("Select inbox to read from: ");
        String inbox = System.console().readLine();

        outputStreamWriter.write("a002 EXAMINE "
                + inbox);
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();

        readUntilTag(in, "a002");
        System.out.println("*** Now to fetch it... ***");

        outputStreamWriter.write("a003 FETCH 1 BODY[text]");
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();

        readUntilTag(in, "a003");

        //KeyStore ks = null;
        //ks = KeyStore.getInstance(protocol);
        //InputStream in = null;
        //in = new FileInputStream(new File());
        //ks.load();
        //SSLContext ctx = SSLContext.getInstance("TLS");

    }

    /*
        Read lines until a received line starts with the same tag
        that was used to tag the LOGIN command with.
    */
    private static void readUntilTag(BufferedReader bufferedReader, String tag) throws IOException {
        String line = bufferedReader.readLine();
        while(line != null){
            System.out.println(line);
            if(line.contains(tag)){
                break;
            }
            line = bufferedReader.readLine();
        }
    }
}
