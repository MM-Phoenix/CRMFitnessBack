package crmfitness.project.service;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.model.User;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@AllArgsConstructor
public abstract class SendWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void send(User user, String session, String message) {
        messagingTemplate.convertAndSendToUser(session, "/queue/messages", new ChatMessage(getFromWho(user), message));
    }

    abstract String getFromWho(User user);
}
