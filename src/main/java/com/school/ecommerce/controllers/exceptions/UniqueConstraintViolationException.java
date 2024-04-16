package com.school.ecommerce.controllers.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UniqueConstraintViolationException extends DataIntegrityViolationException {
    public UniqueConstraintViolationException(String msg) {
        super(msg);
    }
}
