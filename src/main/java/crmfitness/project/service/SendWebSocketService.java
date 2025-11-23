package crmfitness.project.service;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.data.SocketKey;
import crmfitness.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public abstract class SendWebSocketService {

    private SimpMessagingTemplate messagingTemplate;

    public void send(User user, SocketKey socketKey, String message) {
        messagingTemplate.convertAndSend(
                "/queue/" + socketKey.getUrlPath() + "/messages",
                new ChatMessage(getFromWho(user), message));
    }

    public void send(SocketKey socketKey, String message) {
        messagingTemplate.convertAndSend(
                "/queue/" + socketKey.getUrlPath() + "/messages",
                new ChatMessage("Assistant", message));
    }

    abstract String getFromWho(User user);

    @Lazy
    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
}
