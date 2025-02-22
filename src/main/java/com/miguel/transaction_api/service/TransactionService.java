package com.miguel.transaction_api.service;

import com.miguel.transaction_api.dto.TransactionRequest;
import com.miguel.transaction_api.exception.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final List<TransactionRequest> transactionRequests = new ArrayList<>();

    public void addTransaction(TransactionRequest dto) {
        log.info("Starting the process to add a new transaction");

        if (dto.dateTime().isAfter(OffsetDateTime.now())) {
            log.error("Date and time later than the current date and time");
            throw new UnprocessableEntity("Date and time later than the current date and time");
        }
        if (dto.value() < 0) {
            log.error("Negative values are not allowed");
            throw new UnprocessableEntity("Values less than 0 are not allowed");
        }

        transactionRequests.add(dto);
        log.info("Transactions successfully added");
    }

    public void clearTransactions() {
        log.info("Started clearing transactions");
        transactionRequests.clear();
        log.info("Finished clearing transactions");
    }

    public List<TransactionRequest> findTransactionsInTime(Long interval) {
        log.info("Started searching for transactions statistics in the interval of {}", interval);
        OffsetDateTime range = OffsetDateTime.now().minusSeconds(interval);
        log.info("Successful in returning transactions");
        return transactionRequests.stream().filter(dto -> dto.dateTime().isAfter(range)).toList();
    }
}
