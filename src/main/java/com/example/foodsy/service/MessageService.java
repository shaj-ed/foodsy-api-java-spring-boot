package com.example.foodsy.service;

import com.example.foodsy.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Page<Message> getMessageByConversation(Pageable pageable, Long conversationId);
}
