package tech.chickies.notificationserver.mail;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
@AllArgsConstructor
public class EmailSendServiceImpl implements EmailSendService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    @Override
    public  void sendBasicEmail(EmailDTO email) {
        String emailContent = null;
        if(email.getTemplate() != null){
            Context context = new Context();
            if(email.getContext()!=null){
                context.setVariables(email.getContext());
            }
            emailContent = templateEngine.process(email.getTemplate(),context);
        } else {
            emailContent = email.getContent();
        }
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setFrom("auth.service@coalition.io");
            mimeMessageHelper.setText(emailContent,true);
            javaMailSender.send(mimeMessage);
        }catch(Exception e){
//            TODO log
            System.out.println(e.getMessage());
        }
    }
}
