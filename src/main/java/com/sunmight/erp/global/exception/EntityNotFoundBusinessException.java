package com.sunmight.erp.global.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundBusinessException extends RuntimeException {

    private final String entityName;
    private final Object identifier;

    public EntityNotFoundBusinessException(String entityName, Object identifier) {
        super(entityName + " not found. identifier=" + identifier);
        this.entityName = entityName;
        this.identifier = identifier;
    }
}
