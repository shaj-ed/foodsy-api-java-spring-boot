package com.example.foodsy.controller;

import com.example.foodsy.dto.ConversationDTO;
import com.example.foodsy.service.ConversationService;
import com.example.foodsy.service.impl.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @GetMapping
    ResponseEntity<?> getConversations(@AuthenticationPrincipal CustomUserDetailsImpl userDetails) {
        List<ConversationDTO> conversationDTOS = conversationService.getConversations(userDetails.getId());

        return ResponseEntity.ok(Map.of(
                "message", "Successful",
                "data", conversationDTOS
        ));
    }

    @PostMapping("/add")
    ResponseEntity<?> addConversation(
            @RequestParam(name = "senderId") Long senderId,
            @RequestParam(name = "receiverId") Long receiverId
    ) {
        ConversationDTO conversationDTO = conversationService.addConversation(senderId, receiverId);

        return ResponseEntity.ok(Map.of(
                "message", "Add Successful",
                "data", conversationDTO
        ));
    }
}
