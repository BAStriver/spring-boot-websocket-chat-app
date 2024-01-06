package com.bas.websocket.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatInRoomController {
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}") // if not add @SendTo, then by default will send to the path /topic/chat/{roomId}
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
        log.info("roomId: {}", roomId);
        return message;
    }

    // if need the {roomId} in @SendTo,
    // then should add {roomId} in @MessageMapping and sent roomId from front end.
    // otherwise, it could not resolve placeholder 'roomId' in value "/topic/chat/{roomId} of @SendTo
    @MessageMapping("/chat.addUserToRoom/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
