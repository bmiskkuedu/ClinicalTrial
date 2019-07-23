package com.bmi.clinicaltrial.exception;

public class CustomException extends Exception
{
    public CustomAdvice.ErrorCode error;
    public String message;

    public CustomException(CustomAdvice.ErrorCode error, String message)
    {
        this.error = error;
        this.message = message;
    }

    public CustomException(CustomAdvice.ErrorCode code)
    {
        this.error = code;
    }


    public CustomException(String message)
    {
        this.message = message;
    }
}
