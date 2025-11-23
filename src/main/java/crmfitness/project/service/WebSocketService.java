package crmfitness.project.service;

import crmfitness.project.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static crmfitness.project.model.Role.*;

@Service
@AllArgsConstructor
public class WebSocketService {

    private final ClientAdminChatService clientAdminChatService;
    private final AdminSendWebSocketService adminSendWebSocketService;
    private final ClientSendWebSocketService clientSendWebSocketService;

    public void send(User user, String session, String message) {
        if (user != null &&
                (OWNER.equals(user.getRole()) || ADMIN.equals(user.getRole()) || TRAINER.equals(user.getRole()))) {
            clientAdminChatService.getUserForAdmin(session).ifPresentOrElse(userSocketKey ->
                    adminSendWebSocketService.send(user, userSocketKey, message),
                    () -> clientAdminChatService.getAdmin(session).ifPresent(adminSocketKey ->
                            clientSendWebSocketService.send(adminSocketKey, "There are no clients")));
        } else {
            clientAdminChatService.getAdminForUser(session).ifPresentOrElse(adminSocketKey ->
                    clientSendWebSocketService.send(user, adminSocketKey, message),
                    () -> clientAdminChatService.getClient(session).ifPresent(clientSocketKey ->
                            clientSendWebSocketService.send(clientSocketKey, "There are no admins")));
        }
    }
}
