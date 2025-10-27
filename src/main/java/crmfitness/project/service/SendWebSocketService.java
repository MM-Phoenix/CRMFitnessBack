package crmfitness.project.service;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public abstract class SendWebSocketService {

    private SimpMessagingTemplate messagingTemplate;

    public void send(User user, String session, String message) {
        messagingTemplate.convertAndSendToUser(session, "/queue/messages", new ChatMessage(getFromWho(user), message));
    }

    abstract String getFromWho(User user);

    @Lazy
    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
}
