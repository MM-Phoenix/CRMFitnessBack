package crmfitness.project.socket;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final WebSocketListener webSocketListener;

    @NonNull
    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand().equals(StompCommand.CONNECT)) {
            webSocketListener.onConnect(accessor);
        }

        if (accessor.getCommand().equals(StompCommand.SUBSCRIBE)) {
            webSocketListener.onSubscribe(accessor);
        }

        if (accessor.getCommand().equals(StompCommand.DISCONNECT)) {
            webSocketListener.onDisconnect(accessor);
        }

        return message;
    }
}
