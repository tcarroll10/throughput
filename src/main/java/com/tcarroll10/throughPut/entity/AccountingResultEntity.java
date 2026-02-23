package com.tcarroll10.throughPut.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounting_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountingResultEntity {

    @Id
    private String id;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String payload;

    @Column(nullable = false)
    private long timestamp;

    @Column(nullable = false)
    private LocalDateTime receivedAt;

    @Column(nullable = true)
    private String batchId;
}
