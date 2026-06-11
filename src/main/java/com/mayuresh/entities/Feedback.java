package com.mayuresh.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Feedback {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(nullable = false)
    private int rating;   // 1–5

    private String comment;

    private LocalDateTime createdAt;

    // Many feedbacks → One user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //  Many feedbacks → One route
    @ManyToOne
    
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    // Feedback moderation status
    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;
	
}
