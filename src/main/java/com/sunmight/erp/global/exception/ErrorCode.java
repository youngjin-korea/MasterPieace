package com.sunmight.erp.global.exception;

public enum ErrorCode {
    ENTITY_NOT_FOUND(404, "ENTITY_NOT_FOUND", "Requested entity does not exist."),
    VALIDATION_ERROR(400, "VALIDATION_ERROR", "Invalid request data."),
    BUSINESS_ERROR(400, "BUSINESS_ERROR", "Business rule violated."),
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "Internal server error.");


    private final int status;
    private final String code;
    private final String message;


    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
