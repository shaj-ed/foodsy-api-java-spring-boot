package com.example.foodsy.controller;

import com.example.foodsy.dto.ChatMessageDTO;
import com.example.foodsy.dto.MessageSentDTO;
import com.example.foodsy.entity.Message;
import com.example.foodsy.mapper.MessageMapper;
import com.example.foodsy.service.MessageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageSentDTO messageSentDTO) {
        messageService.sentMessage(messageSentDTO);
        return ResponseEntity.ok(Map.of(
                "message", "Message send"
        ));
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getMessageByConversation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @PathVariable Long conversationId) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Message> messages = messageService.getMessageByConversation(pageable, conversationId);
        if(messages.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "message", "No messages available",
                    "data", ""
            ));
        } else {
            List<ChatMessageDTO> messageDTOS = messages.stream()
                    .map(MessageMapper::toResponseChatMessage)
                    .toList();

            return ResponseEntity.ok(Map.of(
                    "message", "Success",
                    "data", messageDTOS,
                    "pagination", Map.of(
                            "currentPage", messages.getNumber(),
                            "limit", messages.getSize(),
                            "totalPages", messages.getTotalPages(),
                            "totalItems", messages.getTotalElements(),
                            "hasNext", messages.hasNext(),
                            "hasPrevious", messages.hasPrevious()
                    )
            ));
        }
    }
}
