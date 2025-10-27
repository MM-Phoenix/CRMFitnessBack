package crmfitness.project.service;

import crmfitness.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class AdminSendWebSocketService extends SendWebSocketService {

    @Override
    String getFromWho(User user) {
        return "Admin: " + user.getFirstName();
    }
}
