package com.tcarroll10.throughPut.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tcarroll10.throughPut.entity.AccountingResultEntity;
import com.tcarroll10.throughPut.repository.AccountingResultRepository;
import com.tcarroll10.throughPut.to.AccountingResult;

@Service
public class RecordService {

    private final AccountingResultRepository repository;

    public RecordService(AccountingResultRepository repository) {
        this.repository = repository;
    }

    public AccountingResult save(AccountingResult record) {
        AccountingResultEntity entity = toEntity(record, null);
        repository.save(entity);
        return record;
    }

    public String saveAll(List<AccountingResult> records) {
        String batchId = UUID.randomUUID().toString();
        List<AccountingResultEntity> entities = records.stream()
                .map(r -> toEntity(r, batchId))
                .toList();
        repository.saveAll(entities);
        return batchId;
    }

    private AccountingResultEntity toEntity(AccountingResult record, String batchId) {
        return AccountingResultEntity.builder()
                .id(record.id())
                .payload(record.payload())
                .timestamp(record.timestamp())
                .receivedAt(LocalDateTime.now())
                .batchId(batchId)
                .build();
    }
}
