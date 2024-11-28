package com.vedruna.proyectoFinalServidor1.dto;




public class ResponseDTO<T> {

    private String message;
    private T data;


    // Constructor
    public ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
    

