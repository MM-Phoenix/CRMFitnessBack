package crmfitness.project.socket;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.data.SocketKey;
import crmfitness.project.jwt.service.JwtService;
import crmfitness.project.model.User;
import crmfitness.project.service.ClientAdminChatService;
import crmfitness.project.service.UserService;
import crmfitness.project.service.WebSocketService;
import crmfitness.project.visitor.Admin;
import crmfitness.project.visitor.Client;
import crmfitness.project.visitor.UnregisteredClient;
import crmfitness.project.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static crmfitness.project.model.Role.*;

@Log4j2
@Component
@AllArgsConstructor
public class WebSocketListener {

    private final Visitor visitor;

    private final JwtService jwtService;
    private final UserService userService;

    private final WebSocketService webSocketService;
    private final ClientAdminChatService clientAdminChatService;

    public void onConnect(StompHeaderAccessor accessor) {
        User user = getUser(accessor);
        String session = accessor.getSessionId();

        String userName = user == null ? "Unknown" : user.getFirstName() + " " + user.getLastName();
        log.info(userName + " with session " + session + " connected.");
    }

    public void onSubscribe(StompHeaderAccessor accessor) {
        User user = getUser(accessor);
        String urlPath = urlPath(accessor);
        String session = accessor.getSessionId();

        if (user != null &&
                (OWNER.equals(user.getRole()) || ADMIN.equals(user.getRole()) || TRAINER.equals(user.getRole()))) {
            new Admin(user, session).accept(visitor);
            clientAdminChatService.addAdmin(session, new SocketKey(session, urlPath));
        } else {
            if (user != null) {
                new Client(user, session).accept(visitor);
            } else {
                new UnregisteredClient(user, session).accept(visitor);
            }

            clientAdminChatService.addClient(session, new SocketKey(session, urlPath));
            clientAdminChatService.assignAdminToUser(session);
        }
    }

    public void onSend(StompHeaderAccessor accessor, ChatMessage message) {
        User user = getUser(accessor);
        String session = accessor.getSessionId();

        webSocketService.send(user, session, message.getContent());
    }

    public void onDisconnect(StompHeaderAccessor accessor) {
        String session = accessor.getSessionId();

        visitor.leave(session);
        clientAdminChatService.removeAdmin(session);
        clientAdminChatService.removeClient(session);
    }

    private User getUser(StompHeaderAccessor accessor) {
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

    private String urlPath(StompHeaderAccessor accessor) {
        String urlPath = accessor.getFirstNativeHeader("urlPath");

        if(urlPath == null) {
            throw new RuntimeException("Illegal url path");
        }

        return urlPath;
    }
}
