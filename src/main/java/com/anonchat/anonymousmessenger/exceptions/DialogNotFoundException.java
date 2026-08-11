package com.anonchat.anonymousmessenger.exceptions;

public class DialogNotFoundException extends RuntimeException {
    public DialogNotFoundException(String message) {
        super(message);
    }
}
