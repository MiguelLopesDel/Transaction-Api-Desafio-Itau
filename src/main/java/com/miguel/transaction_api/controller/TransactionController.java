package com.miguel.transaction_api.controller;

import com.miguel.transaction_api.dto.TransactionRequest;
import com.miguel.transaction_api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    @Operation(description = "Endpoint responsável por adicionar transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação gravada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Campos não atendem requisitos para a transação"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<Void> registerTransaction(@RequestBody TransactionRequest request) {
        service.addTransaction(request);
        return ResponseEntity.status(CREATED).build();
    }
}
