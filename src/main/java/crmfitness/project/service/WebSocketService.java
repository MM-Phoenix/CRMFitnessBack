package crmfitness.project.service;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.model.Role;
import crmfitness.project.model.User;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ClientAdminChatService clientAdminChatService;

    public void send(User user, String session, ChatMessage message) {
        if (user != null && Role.ADMIN.equals(user.getRole())) {
            clientAdminChatService.getUserForAdmin(session).ifPresent(userSession -> send(userSession, message));
        } else {
            clientAdminChatService.getAdminForUser(session).ifPresent(adminSession -> send(adminSession, message));
        }
    }

    public void send(String session, ChatMessage message) {
        messagingTemplate.convertAndSendToUser(session, "/queue/messages", message);
    }
}
