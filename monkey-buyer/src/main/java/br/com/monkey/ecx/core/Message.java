package br.com.monkey.ecx.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message<T> implements Serializable {

    @JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
    private T message;

    private String userName;

    private String userIp;

    public Message(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserIp() {
        return this.userIp;
    }
}
