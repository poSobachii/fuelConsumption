package com.fuel.fuelConsumption.exception;

import com.fuel.fuelConsumption.rest.Utility;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(final String message) {
        super(message);
    }
}
