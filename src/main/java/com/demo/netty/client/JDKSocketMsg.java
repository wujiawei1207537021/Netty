package com.demo.netty.client;

import lombok.Data;

import java.io.Serializable;

@Data
public class JDKSocketMsg implements Serializable {
    private String type="chat";
    private Object msg;
}
