import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class Send {
    public static void main(String[] args) {
        try{
            int portSSL = 993;
            int portSMTP = 587;
            String host = "smtp.kth.se";


            String receiver = "****";
            String username = "****";
            String from = username+"@kth.se";
            String mailEnc = Base64.getEncoder().encodeToString(username.getBytes());
            String password  = "****";
            String passwordEnc = Base64.getEncoder().encodeToString(password.getBytes());

            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.from", from);
            mailProps.put("mail.smtp.host", host);
            mailProps.put("mail.smtp.port", portSMTP);
            mailProps.put("mail.smtp.auth", true);
            mailProps.put("mail.smtp.socketFactory.port", portSSL);
            mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            mailProps.put("mail.smtp.starttls.enable", true);

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };


            Session session = Session.getDefaultInstance(mailProps, auth);
            //Session session = Session.getInstance(mailProps);
            System.out.println("0");
            //sendEmail(session, mail,"Test Subject", "Test Body");

            String subject = "Test";
            String body = "Hello Mail!";

            Message message = new MimeMessage(session);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver, false));
            message.setText(body);
            /*
            //MimeMessage message = new MimeMessage(session);

            //set message headers
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            message.setFrom(new InternetAddress(from));
            message.setReplyTo(InternetAddress.parse(from, false));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            message.setSentDate(new Date());

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            */
            //Transport transport = session.getTransport();
            //InternetAddress addressFrom = new InternetAddress(mail);
            System.out.println("1");
            /*
            Transport tr = session.getTransport("smtp");
            tr.connect(host, username, password);
            mimeMessage.saveChanges();      // don't forget this
            System.out.println("2");
            tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            tr.close();
            */
            Transport.send(message);
            System.out.println("Sent!");


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}