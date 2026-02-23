package com.example.foodsy.mapper;

import com.example.foodsy.dto.ConversationDTO;
import com.example.foodsy.entity.Conversation;

public class ConversationMapper {
    public static ConversationDTO toConversationResponse(Conversation conversation) {
        return ConversationDTO.builder()
                .id(conversation.getId())
                .senderId(conversation.getSender().getId())
                .receiverId(conversation.getReceiver().getId())
                .senderName(conversation.getSender().getUsername())
                .receiverName(conversation.getReceiver().getUsername())
                .build();
    }
}
