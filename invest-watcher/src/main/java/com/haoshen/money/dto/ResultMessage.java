package com.haoshen.money.dto;

import lombok.Data;

@Data
public class ResultMessage<E> {

    private E result;

    private String message;

}
