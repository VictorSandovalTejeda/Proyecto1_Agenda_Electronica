/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 https://www.google.com/settings/security/lesssecureapps
 */
package proyecto_agenda;

/**
 *
 * @author pc HP
 */
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    
    private String emailUser = "vas.tejeda@gmail.com";
    private String emailPass = "Lievanos2501";

    public boolean validateEmail(String user) {

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(user);

        return matcher.find();
    }
    
    public void sendEmail (String correoNotificar, String nombreEvento, String descripcionEvento ){
        try{
            String host ="smtp.gmail.com" ;
            String user = emailUser;
            String pass = emailPass;
            String to = correoNotificar;
            String from = emailUser;
            String subject = nombreEvento;
            String messageText = descripcionEvento;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoNotificar));
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println(Console_Colors.ANSI_GREEN + "Mensaje Enviado!" + Console_Colors.ANSI_RESET);
        }catch(Exception ex)
        {
            System.out.println(Console_Colors.ANSI_RED + "Mensaje no fue enviado." + Console_Colors.ANSI_RESET);
            System.out.println(ex);
        }
    }

}
