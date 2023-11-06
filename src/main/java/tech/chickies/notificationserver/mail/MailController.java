package tech.chickies.notificationserver.mail;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @PostMapping("/send/batch")
    public Boolean sendEmailBatch (@RequestBody EmailDTO emailDTO){
        try {
            List<String> tos = emailDTO.getTos();
            if(emailDTO.getTos() == null || emailDTO.getTos().size() <= 0){
                return false;
            }
            int numThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

            for (String email : tos) {
                emailDTO.setTo(email);
                executorService.submit(() -> emailSendService.sendBasicEmail(emailDTO));
            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}