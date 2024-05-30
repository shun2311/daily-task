package com.example.task.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ClaimStatus claimStatus;

    @DecimalMin(value = "0")
    @Column(precision = 19, scale = 6, name = "amount", nullable = false)
    private BigDecimal reward;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
