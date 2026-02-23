package com.example.foodsy.mapper;

import com.example.foodsy.dto.ChatMessageDTO;
import com.example.foodsy.entity.Message;

public class MessageMapper {
    public static ChatMessageDTO toResponseChatMessage(Message message) {
        return ChatMessageDTO.builder()
                .id(message.getId())
                .conversationId(message.getConversation().getId())
                .messageText(message.getMessageText())
                .sendAt(message.getCreatedAt())
                .build();
    }
}
