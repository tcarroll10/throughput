package com.tcarroll10.throughPut.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcarroll10.throughPut.service.RecordService;
import com.tcarroll10.throughPut.to.AccountingResult;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<AccountingResult> createRecord(@RequestBody AccountingResult record) {
        AccountingResult saved = recordService.save(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AccountingResult>> createRecords(@RequestBody List<AccountingResult> records) {
        List<AccountingResult> saved = recordService.saveAll(records);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
