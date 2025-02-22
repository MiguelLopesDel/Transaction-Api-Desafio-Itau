package com.miguel.transaction_api.service;

import com.miguel.transaction_api.dto.StatisticsResponse;
import com.miguel.transaction_api.dto.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {

    public final TransactionService service;

    public StatisticsResponse calculateTransactionStatistics(Long interval) {
        log.info("Started calculating transactions statistics");
        long start = System.currentTimeMillis();
        List<TransactionRequest> transactions = service.findTransactionsInTime(interval);
        if (transactions.isEmpty())
            return new StatisticsResponse(0L, 0D, 0D, 0D, 0D);
        DoubleSummaryStatistics summary = transactions.stream()
                .mapToDouble(TransactionRequest::value).summaryStatistics();
        log.info(String.format("Successful in returning statistics. Time to calculate statistics in millis: %d", (System.currentTimeMillis() - start)));
        return new StatisticsResponse(summary.getCount(), summary.getSum(), summary.getAverage(), summary.getMin(), summary.getMax());
    }

}
