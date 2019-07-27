package com.bmi.clinicaltrial.parser;

import com.bmi.clinicaltrial.exception.CustomException;

import java.util.List;

public abstract class BaseParser
{
    public abstract <T, E> void Test(List<T> list, E e) throws CustomException;

}
