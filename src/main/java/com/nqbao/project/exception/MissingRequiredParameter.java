package com.nqbao.project.exception;

public class MissingRequiredParameter extends RuntimeException {
    public MissingRequiredParameter(String message) {
        super(message);
    }

}
