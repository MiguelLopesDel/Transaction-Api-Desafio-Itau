package com.miguel.transaction_api.service;

import com.miguel.transaction_api.dto.TransactionRequest;
import com.miguel.transaction_api.exception.UnprocessableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;
    private final TransactionRequest dto = new TransactionRequest(1D, OffsetDateTime.now());

    @Test
    void shouldAddTransaction() {
        service.addTransaction(dto);
        List<TransactionRequest> transactions = service.findTransactionsInTime(9999L);
        assertTrue(transactions.contains(dto));
    }

    @Test
    void shouldThrowUnprocessableEntityByDateIsAfter() {
        assertThrows(UnprocessableEntity.class, () -> service.addTransaction(new TransactionRequest(1D, OffsetDateTime.now().plusSeconds(1))));
    }

    @Test
    void shouldThrowUnprocessableEntityByNegativeValues() {
        assertThrows(UnprocessableEntity.class, () -> service.addTransaction(new TransactionRequest(-1D, OffsetDateTime.MIN)));
    }

    @Test
    void shoudExecuteClear() {
        service.clearTransactions();
        List<TransactionRequest> transactions = service.findTransactionsInTime(9999999L);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void shouldReturnTransactionsInTime() {
        Long interval = 3600L;
        OffsetDateTime now = OffsetDateTime.now();
        TransactionRequest validTransaction = new TransactionRequest(1D, now.minusMinutes(30));
        TransactionRequest outdatedTransaction = new TransactionRequest(1D, now.minusHours(2));
        service.addTransaction(validTransaction);
        service.addTransaction(outdatedTransaction);

        List<TransactionRequest> result = service.findTransactionsInTime(interval);
        assertTrue(result.contains(validTransaction));
        assertFalse(result.contains(outdatedTransaction));
    }
}