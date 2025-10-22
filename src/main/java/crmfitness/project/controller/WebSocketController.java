package crmfitness.project.controller;

import crmfitness.project.data.ChatMessage;
import crmfitness.project.socket.WebSocketListener;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final WebSocketListener webSocketListener;

    @MessageMapping("/chat/send")
    public void handleMessage(StompHeaderAccessor accessor, @Payload ChatMessage message) {
        webSocketListener.onSend(accessor, message);
    }
}
