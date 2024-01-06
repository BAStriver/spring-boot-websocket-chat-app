package com.bas.websocket.chat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;

}
