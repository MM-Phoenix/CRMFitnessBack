package crmfitness.project.service;

import crmfitness.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class ClientSendWebSocketService extends SendWebSocketService {

    @Override
    String getFromWho(User user) {
        if (user == null) {
            return "Unknown client";
        } else {
            return "Client: " + user.getFirstName() + " " + user.getLastName();
        }
    }
}
