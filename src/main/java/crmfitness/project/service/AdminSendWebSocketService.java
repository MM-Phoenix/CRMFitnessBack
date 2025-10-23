package crmfitness.project.service;

import crmfitness.project.model.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdminSendWebSocketService extends SendWebSocketService {

    public AdminSendWebSocketService(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    @Override
    String getFromWho(User user) {
        return "Admin: " + user.getFirstName();
    }
}
