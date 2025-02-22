package com.miguel.transaction_api.controller;

import com.miguel.transaction_api.dto.StatisticsResponse;
import com.miguel.transaction_api.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estatistica")
public class StatisticController {

    private final StatisticService service;

    @GetMapping
    @Operation(description = "Endpoint responsável por adicionar transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<StatisticsResponse> getTransactionsInTime(
            @RequestParam(value = "interval", required = false, defaultValue = "60") Long interval) {
        return ResponseEntity.ok(service.calculateTransactionStatistics(interval));
    }
}
