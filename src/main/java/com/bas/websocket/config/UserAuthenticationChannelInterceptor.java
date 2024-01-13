package com.bas.websocket.config;

import com.bas.websocket.model.StompAuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

@Slf4j
public class UserAuthenticationChannelInterceptor implements ChannelInterceptor {

    private static final String USER_ID = "User-ID";
    private static final String USER_NAME = "User-Name";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userID = accessor.getFirstNativeHeader(USER_ID); // always different after every connections
            String username = accessor.getFirstNativeHeader(USER_NAME); // user name + user unique id

            log.info("Stomp User-Related headers found, userID: {}, username:{}", userID, username);
            accessor.setUser(new StompAuthenticatedUser(userID, username));
        }

        return message;
    }

}
