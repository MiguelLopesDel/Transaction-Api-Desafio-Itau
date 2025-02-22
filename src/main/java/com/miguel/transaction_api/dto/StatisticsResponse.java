package com.miguel.transaction_api.dto;

public record StatisticsResponse(Long count, Double sum, Double avg, Double min, Double max) {}
