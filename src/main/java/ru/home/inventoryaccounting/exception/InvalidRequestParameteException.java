package ru.home.inventoryaccounting.exception;

public class InvalidRequestParameteException extends RuntimeException {
    public InvalidRequestParameteException(String message) {
        super(message);
    }
}
