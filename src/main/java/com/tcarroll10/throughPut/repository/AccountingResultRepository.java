package com.tcarroll10.throughPut.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcarroll10.throughPut.entity.AccountingResultEntity;

public interface AccountingResultRepository extends JpaRepository<AccountingResultEntity, String> {
}
