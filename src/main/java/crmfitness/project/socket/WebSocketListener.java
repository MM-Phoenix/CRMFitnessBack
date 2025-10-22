package crmfitness.project.socket;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.jwt.service.JwtService;
import crmfitness.project.model.Role;
import crmfitness.project.model.User;
import crmfitness.project.service.ClientAdminChatService;
import crmfitness.project.service.UserService;
import crmfitness.project.service.WebSocketService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class WebSocketListener {

    private final JwtService jwtService;
    private final UserService userService;

    private final WebSocketService webSocketService;
    private final ClientAdminChatService clientAdminChatService;

    public void onConnect(StompHeaderAccessor accessor) {
        //Can use for other services. For example to display connections count.

        User user = getUser(accessor);
        String session = accessor.getSessionId();

        String userName = user == null ? "Unknown" : user.getFirstName() + " " + user.getLastName();
        log.info(userName + " with session " + session + " connected.");
    }

    public void onSubscribe(StompHeaderAccessor accessor) {
        User user = getUser(accessor);
        String session = accessor.getSessionId();

        if (user != null && Role.ADMIN.equals(user.getRole())) {
            clientAdminChatService.addAdmin(session, user);
        } else {
            clientAdminChatService.addClient(session);
            String message = clientAdminChatService.assignAdminToUser(session);
            webSocketService.send(session, new ChatMessage(message));
        }
    }

    public void onSend(StompHeaderAccessor accessor, ChatMessage message) {
        User user = getUser(accessor);
        String session = accessor.getSessionId();

        webSocketService.send(user, session, message);
    }

    public void onDisconnect(StompHeaderAccessor accessor) {
        String session = accessor.getSessionId();

        clientAdminChatService.removeAdmin(session);
        clientAdminChatService.removeClient(session);
    }


    public User getUser(StompHeaderAccessor accessor) {
        String authHeader = accessor.getFirstNativeHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String jwt = authHeader.substring(7);
            final Long userId = jwtService.extractUserId(jwt);
            User user;

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null &&
                    jwtService.isTokenValid(jwt, (user = userService.getById(userId)))) {
                return user;
            } else {
                throw new RuntimeException("Illegal authorization");
            }
        }
        return null;
    }
}
