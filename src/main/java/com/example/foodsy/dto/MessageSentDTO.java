package com.example.foodsy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSentDTO {
    @NotNull(message = "Conversation ID is required")
    private Long conversationId;
    @NotNull(message = "Sender ID is required")
    private Long senderId;
    @NotNull(message = "Receiver ID is required")
    private Long receiverId;
    @NotNull(message = "Message text is required")
    private String messageText;
}
