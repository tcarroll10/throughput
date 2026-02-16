package com.tcarroll10.throughPut.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcarroll10.throughPut.to.AccountingResult;

@Service
public class RecordService {

    public AccountingResult save(AccountingResult record) {
        return record;
    }

    public List<AccountingResult> saveAll(List<AccountingResult> records) {
        return records;
    }
}
