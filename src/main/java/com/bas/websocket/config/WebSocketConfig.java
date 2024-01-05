package com.bas.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // register STOMP endpoints
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // this is the endpoint which should be set in SockJS client
                .setAllowedOriginPatterns("*") // allow cross-domain request
                .withSockJS(); // use SockJS protocol
    }

    // register message broker
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // while sending messages in front end, the path should add the prefix as /app
        registry.setApplicationDestinationPrefixes("/app");

        // set the prefixes of broker paths, like /topic/public
        registry.enableSimpleBroker("/topic");

        // while sending messages to user in front end, the path should add the prefix as /user
        // default is /user
        registry.setUserDestinationPrefix("/user");
    }
}
