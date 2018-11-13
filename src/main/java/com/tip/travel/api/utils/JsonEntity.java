package com.tip.travel.api.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class JsonEntity<T> implements Serializable {
    private static final long serialVersionUID = -1771426378340695807L;
    @ApiModelProperty("Actual response data as JSON")
    T data;
    @ApiModelProperty(
        value = "API http response status. 200 is ok, 500 is failed.",
        example = "200",
        required = true
    )
    private int status = 200;
    @ApiModelProperty("Human readable response message")
    private String message;

    public JsonEntity() {
    }

    public JsonEntity(T data) {
        this.data = data;

    }

    public JsonEntity(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return this.status;
    }

    public JsonEntity setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public JsonEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public JsonEntity setData(T data) {
        this.data = data;
        return this;
    }

    @JsonIgnore
    public boolean normal() {
        return this.status == 200;
    }

    @Override
    public String toString() {
        return "JsonEntity{" +
               "data=" + data +
               ", status=" + status +
               ", message='" + message + '\'' +
               '}';
    }
}
