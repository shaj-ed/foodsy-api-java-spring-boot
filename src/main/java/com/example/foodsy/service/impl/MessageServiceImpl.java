package com.example.foodsy.service.impl;

import com.example.foodsy.entity.Message;
import com.example.foodsy.repository.MessageRepository;
import com.example.foodsy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Page<Message> getMessageByConversation(Pageable pageable, Long conversationId) {
        return messageRepository.findMessagesByConversation(conversationId, pageable);
    }
}
