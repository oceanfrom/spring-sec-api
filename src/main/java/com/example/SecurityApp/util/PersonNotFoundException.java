    package com.example.SecurityApp.util;

    public class PersonNotFoundException extends RuntimeException{
        public PersonNotFoundException(String message) {
            super(message);
        }
    }
