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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public void send(String correo, String password, String correoNotificar, String nombreEvento, String descripcionEvento) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(correo, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correoNotificar));
            message.setSubject(nombreEvento);
            message.setText(descripcionEvento);

            Transport.send(message);

            System.out.println(Console_Colors.ANSI_GREEN + "El Correo de Notificacion a sido enviado!" + Console_Colors.ANSI_RESET);

        } catch (MessagingException e) {
            System.out.println(Console_Colors.ANSI_RED + "**ERROR AL ENVIAR EL MENSAJE" + Console_Colors.ANSI_RESET);
            System.out.println(e);
        }
    }

}
