package tech.chickies.notificationserver.mail;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {

    @Autowired
    private final EmailSendService emailSendService;
    @PostMapping("/send")
    public Boolean sendEmail (@RequestBody EmailDTO emailDTO){
        try {
            emailSendService.sendBasicEmail(emailDTO);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}