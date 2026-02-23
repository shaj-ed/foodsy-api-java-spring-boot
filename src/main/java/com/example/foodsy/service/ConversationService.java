package com.example.foodsy.service;

import com.example.foodsy.dto.ConversationDTO;

import java.util.List;

public interface ConversationService {
    List<ConversationDTO> getConversations(Long userid);
    ConversationDTO addConversation(Long senderId, Long receiverId);
}