package com.bmi.clinicaltrial.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomAdvice
{
    public enum ErrorCode
    {
        INVAILD_BIRTHDATE,
        CODE2,
        CODE3,
        CODE4;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity result(CustomException ce)
    {
        return ResponseEntity.badRequest().body(ce.message);
    }
}
