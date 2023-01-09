import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;

public class Receive {

    public static void main(String[] args) {
        try{
            Scanner in;
            in = new Scanner(System.in);

            String host = "webmail.kth.se";
            String username = "****";
            String password  = "****";

            System.out.println("logging in...");

            Properties props =  new Properties();
            props.setProperty("mail.imap.ssl.enable", "true");

            Session session = javax.mail.Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("****");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();

            for (Message m: messages) {
                System.out.println(m.getContent());
            }

            inbox.close(false);
            store.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}