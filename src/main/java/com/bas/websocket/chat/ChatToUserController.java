package com.bas.websocket.chat;

import com.bas.websocket.model.StompAuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatToUserController {

    private final SimpUserRegistry simpUserRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // the path in @MessageMapping should not be duplicated, otherwise will get the error as
    // 'Ambiguous handler methods mapped for destination'
    @MessageMapping("/chatToUser/{userId}")
    @SendTo(value = "/topic/chatToUser/{userId}")
    public ChatMessage sendMessage(@DestinationVariable String userId, ChatMessage message,
                                   SimpMessageHeaderAccessor headerAccessor) {
        log.info("send to the userId: {}", userId);
        log.info("message: {}", message);

//        Set<StompAuthenticatedUser> collect = simpUserRegistry.getUsers().stream()
//                .map(simpUser -> StompAuthenticatedUser.class.cast(simpUser.getPrincipal()))
//                .collect(Collectors.toSet());
//        collect.forEach(user -> {
//            if(user.getNickName().equals(userId)) {
//                simpMessagingTemplate.convertAndSendToUser(userId, "/chatToUser/"+userId, message);
//            }
//        });
        return message;
    }

    @MessageMapping("/chat.helloUser/{userId}")
    @SendTo("/user/chat/{userId}")
    public ChatMessage helloUser(
            @DestinationVariable String userId,
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("userid", userId);

        // use the tool to send the message to public topic directly, without @MessageMapping
        // simpMessagingTemplate.convertAndSend("/user/chat/" + userId, chatMessage);
        return chatMessage;
    }

    //@MessageMapping("/chat.sendMessage")
    @GetMapping("/testSendMessage")
    public void testSendMessage(ChatMessage message) {
        // use the tool to send the message to public topic directly, without @MessageMapping
        simpMessagingTemplate.convertAndSend("/topic/public", message);
    }

    @MessageMapping("/chat/private")
    @SendToUser("/topic/chat/private")
    public ChatMessage privateChat(ChatMessage message) {
        log.info("Private chat message received: {}", message);
        return message;
    }

}
