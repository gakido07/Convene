package Convene.Backend.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class JavaMailSenderConfig {

    @Autowired
    private JavaMailSender javaMailSender;

    private final String SENDER = "noreply@convene.com";

    public void sendSimpleMessage(String recipient, String subject, String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(SENDER);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(
                simpleMailMessage
        );
    }
}
