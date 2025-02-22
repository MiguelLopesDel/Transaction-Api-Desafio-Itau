package com.miguel.transaction_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.miguel.transaction_api.dto.TransactionRequest;
import com.miguel.transaction_api.exception.UnprocessableEntity;
import com.miguel.transaction_api.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private final String endPoint = "/transacao";
    @InjectMocks
    private TransactionController controller;
    @Mock
    private TransactionService service;
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private final TransactionRequest dto = new TransactionRequest(1D, OffsetDateTime.now().minusYears(1));

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldRegisterValidTransaction() throws Exception {
        mockMvc.perform(post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(dto))
        ).andExpect(status().isCreated());
        verify(service, times(1)).addTransaction(any());
    }

    @Test
    void shouldReturn422ForNegativeValue() throws Exception {
        TransactionRequest dtoWithNegativeDTO = new TransactionRequest(-1D, OffsetDateTime.MAX);
        doThrow(new UnprocessableEntity("Values less than 0 are not allowed")).when(service).addTransaction(dtoWithNegativeDTO);
        mockMvc.perform(post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(dtoWithNegativeDTO))
        ).andExpect(status().isUnprocessableEntity());
        verify(service, times(1)).addTransaction(dtoWithNegativeDTO);
    }

    @Test
    void shouldReturn422ForLaterDate() throws Exception {
        TransactionRequest dtoWithLaterDate = new TransactionRequest(1D, OffsetDateTime.MAX);
        doThrow(new UnprocessableEntity("Date and time later than the current date and time")).when(service).addTransaction(dtoWithLaterDate);
        mockMvc.perform(post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(dtoWithLaterDate))
        ).andExpect(status().isUnprocessableEntity());
        verify(service, times(1)).addTransaction(dtoWithLaterDate);
    }

    @Test
    void shouldReturn400ForInvalidRequests() throws Exception {
        String invalidJson = "{ \"value\": -100, \"dateTime\": \"2024-02-20T23:57:27\" ";
        mockMvc.perform(post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson)
        ).andExpect(status().isBadRequest());
    }


    @Test
    void shouldClearingTransactions() throws Exception {
        mockMvc.perform(delete(endPoint)).andExpect(status().isOk());
        verify(service, times(1)).clearTransactions();
    }
}