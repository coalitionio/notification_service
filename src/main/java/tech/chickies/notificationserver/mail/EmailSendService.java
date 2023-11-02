package tech.chickies.notificationserver.mail;
import tech.chickies.notificationserver.mail.EmailDTO;
public interface EmailSendService {
    void sendBasicEmail(EmailDTO email);
}
