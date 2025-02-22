package com.miguel.transaction_api.controller;

import com.miguel.transaction_api.dto.StatisticsResponse;
import com.miguel.transaction_api.service.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StatisticControllerTest {

    @InjectMocks
    private StatisticController controller;
    @Mock
    private StatisticService service;
    private MockMvc mockMvc;
    private StatisticsResponse response = new StatisticsResponse(1L, 10D, 10D, 10D, 10D);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getTransactionsInTime() throws Exception {
        when(service.calculateTransactionStatistics(60L)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(response.count()))
                .andExpect(jsonPath("$.sum").value(response.sum()))
                .andExpect(jsonPath("$.avg").value(response.avg()))
                .andExpect(jsonPath("$.min").value(response.min()))
                .andExpect(jsonPath("$.max").value(response.max()));

    }
}