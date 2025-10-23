package com.mfrakhman.etick.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response<T> {
    private int status;
    private String message;
    private T data;

    // 🟩 Constructors
    public Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 🟦 Static factory methods (helpers)
    public static <T> ResponseEntity<Response<T>> success(T data) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), "Success", data));
    }

    public static <T> ResponseEntity<Response<T>> success(String message, T data) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), message, data));
    }

    public static <T> ResponseEntity<Response<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(HttpStatus.CREATED.value(), message, data));
    }

    public static <T> ResponseEntity<Response<T>> error(int status, String message) {
        return ResponseEntity.status(status)
                .body(new Response<>(status, message, null));
    }

    public static <T> ResponseEntity<Response<T>> error(int status, String message, T data) {
        return ResponseEntity.status(status)
                .body(new Response<>(status, message, data));
    }

    public static <T> ResponseEntity<Response<T>> error(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null));
    }

    // 🟨 Getters
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
