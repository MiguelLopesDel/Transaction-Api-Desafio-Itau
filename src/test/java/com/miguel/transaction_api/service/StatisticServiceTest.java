package com.miguel.transaction_api.service;

import com.miguel.transaction_api.dto.StatisticsResponse;
import com.miguel.transaction_api.dto.TransactionRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @InjectMocks
    private StatisticService statisticService;
    @Mock
    private TransactionService transactionService;
    private final TransactionRequest dto = new TransactionRequest(50D, OffsetDateTime.MIN);
    private final StatisticsResponse statisticsResponse = new StatisticsResponse(1L, 50D,50D,50D,50D);

    @Test
    void calculateTransactionStatisticsSuccessfully() {
        when(transactionService.findTransactionsInTime(60L)).thenReturn(List.of(dto));
        StatisticsResponse realStatisticsResponse = statisticService.calculateTransactionStatistics(60L);

        verify(transactionService, times(1)).findTransactionsInTime(60L);
        Assertions.assertThat(realStatisticsResponse).usingRecursiveComparison().isEqualTo(statisticsResponse);
    }

    @Test
    void calculateTransactionStatisticsWithEmptyList() {
        final StatisticsResponse expectedDTO = new StatisticsResponse(0L,0D,0D,0D,0D);
        when(transactionService.findTransactionsInTime(60L)).thenReturn(List.of());
        StatisticsResponse realStatisticsResponse = statisticService.calculateTransactionStatistics(60L);

        verify(transactionService, times(1)).findTransactionsInTime(60L);
        Assertions.assertThat(realStatisticsResponse).usingRecursiveComparison().isEqualTo(expectedDTO);
    }
}