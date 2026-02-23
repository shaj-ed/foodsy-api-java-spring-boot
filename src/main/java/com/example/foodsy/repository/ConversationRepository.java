package com.example.foodsy.repository;

import com.example.foodsy.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("""
            Select c from Conversation c
            Where (:senderId = c.sender.id OR :senderId = c.receiver.id)
            """)
    List<Conversation> findConversationsBySenderId(@Param("senderId") Long senderId);

    @Query("""
            Select c from Conversation c
            Where (:senderId = c.sender.id AND :receiverId = c.receiver.id)
            OR (:senderId = c.receiver.id AND :receiverId = c.sender.id)
            """)
    Conversation findConversationBySenderIdAndReceiverId(@Param("senderId") Long senderId,
                                                         @Param("receiverId") Long receiverId);
}
