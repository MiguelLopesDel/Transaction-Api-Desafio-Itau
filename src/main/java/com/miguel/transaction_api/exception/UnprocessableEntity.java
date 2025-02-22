package com.miguel.transaction_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntity extends RuntimeException {

    public UnprocessableEntity(String s) {
        super(s);
    }
}
