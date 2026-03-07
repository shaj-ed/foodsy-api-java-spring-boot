package com.example.foodsy.service.impl;

import com.example.foodsy.dto.MessageSentDTO;
import com.example.foodsy.entity.Conversation;
import com.example.foodsy.entity.Message;
import com.example.foodsy.exception.ResourceNotFoundException;
import com.example.foodsy.repository.ConversationRepository;
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

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Page<Message> getMessageByConversation(Pageable pageable, Long conversationId) {
        return messageRepository.findMessagesByConversation(conversationId, pageable);
    }

    @Override
    public void sentMessage(MessageSentDTO messageSentDTO) {
        Conversation conversation = conversationRepository.findById(messageSentDTO.getConversationId())
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        Message newMessage = new Message();
        newMessage.setConversation(conversation);
        newMessage.setSender_id(messageSentDTO.getSenderId());
        newMessage.setReceiver_id(messageSentDTO.getReceiverId());
        newMessage.setMessageText(messageSentDTO.getMessageText());

        messageRepository.save(newMessage);
    }
}
