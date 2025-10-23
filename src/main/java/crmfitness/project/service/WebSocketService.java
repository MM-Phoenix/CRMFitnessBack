package crmfitness.project.service;

import crmfitness.project.model.Role;
import crmfitness.project.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketService {

    private final ClientAdminChatService clientAdminChatService;
    private final AdminSendWebSocketService adminSendWebSocketService;
    private final ClientSendWebSocketService clientSendWebSocketService;

    public void send(User user, String session, String message) {
        if (user != null && Role.ADMIN.equals(user.getRole())) {
            clientAdminChatService.getUserForAdmin(session).ifPresent(userSession ->
                    adminSendWebSocketService.send(user, userSession, message));
        } else {
            clientAdminChatService.getAdminForUser(session).ifPresent(adminSession ->
                    clientSendWebSocketService.send(user, adminSession, message));
        }
    }
}
