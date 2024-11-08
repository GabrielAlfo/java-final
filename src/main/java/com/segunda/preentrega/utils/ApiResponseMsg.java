package com.segunda.preentrega.utils;

import lombok.Data;

@Data
public class ApiResponseMsg {

    private String message;
    private Object data;

    public ApiResponseMsg(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}