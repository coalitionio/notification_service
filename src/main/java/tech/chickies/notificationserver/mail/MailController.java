package tech.chickies.notificationserver.mail;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(originPatterns = {"http://localhost:3000",
        "https://mamakitchen.tech",
        "http://mamakitchen.tech",
        "http://localhost:5173",
        "http://localhost:3001",
        "http://localhost:3002",
        "http://localhost:3003",
        "https://localhost:3000",
        "https://www.mamakitchen.tech"})
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