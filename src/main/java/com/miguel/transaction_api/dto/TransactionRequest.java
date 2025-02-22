package com.miguel.transaction_api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionRequest(Double value, OffsetDateTime dateTime) {}
