package tech.chickies.notificationserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import tech.chickies.notificationserver.chat.ChatMessage;
import tech.chickies.notificationserver.chat.MessageType;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebsocketEventListener {
    private  final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        String username  = (String)headerAccessor.getSessionAttributes().get("username");
        if( username !=null) {
            log.info("User disconnected : {" +username+ "}");
            var chatMessage = ChatMessage.builder()
                    .messageType(MessageType.LEAVE).sender(username)
                    .build();
            messageTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent disconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        String username  = (String)headerAccessor.getSessionAttributes().get("username");
        if( username !=null) {
            log.info("User disconnected : {" +username+ "}");
            var chatMessage = ChatMessage.builder()
                    .messageType(MessageType.LEAVE).sender(username)
                    .build();
            messageTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }


}

