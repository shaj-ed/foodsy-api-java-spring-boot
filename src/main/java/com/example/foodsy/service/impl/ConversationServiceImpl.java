package com.example.foodsy.service.impl;

import com.example.foodsy.dto.ConversationDTO;
import com.example.foodsy.entity.Conversation;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.exception.DuplicateResourceException;
import com.example.foodsy.exception.ResourceNotFoundException;
import com.example.foodsy.mapper.ConversationMapper;
import com.example.foodsy.repository.ConversationRepository;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.ConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public List<ConversationDTO> getConversations(Long userid) {
        UserEntity user = userRepository.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Conversation> conversations = conversationRepository.findConversationsBySenderId(user.getId());
        return conversations.stream()
                .map(ConversationMapper::toConversationResponse)
                .toList();
    }

    @Override
    public ConversationDTO addConversation(Long senderId, Long receiverId) {
        Optional<Conversation> existingConversation =
                Optional.ofNullable(conversationRepository.findConversationBySenderIdAndReceiverId(senderId, receiverId));
        if(existingConversation.isPresent()) {
            throw new DuplicateResourceException("Conversation already exits");
        }

        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + senderId));
        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + receiverId));

        Conversation newConversation = new Conversation();
        newConversation.setSender(sender);
        newConversation.setReceiver(receiver);

        Conversation savedConversation = conversationRepository.save(newConversation);

       return ConversationMapper.toConversationResponse(savedConversation);
    }
}
