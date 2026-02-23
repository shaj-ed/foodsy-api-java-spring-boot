package com.example.foodsy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {
    // This Trigger when user connects to Web Socket
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        log.info("User connected: {}", event.getUser());
    }

    // This trigger when user disconnects to Web Socket
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("User disconnected: {}", event.getUser());
    }

    // Trigger when user subscribe to a channel
    @EventListener
    public void handleSubscriptionEvent(SimpSubscription event) {
        log.info("User subscribed: {}", event.getId());
    }
}
