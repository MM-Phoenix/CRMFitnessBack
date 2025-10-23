package crmfitness.project.service;

import crmfitness.project.model.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClientSendWebSocketService extends SendWebSocketService {

    public ClientSendWebSocketService(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    @Override
    String getFromWho(User user) {
        if (user == null) {
            return "Unknown client";
        } else {
            return "Client: " + user.getFirstName() + " " + user.getLastName();
        }
    }
}
