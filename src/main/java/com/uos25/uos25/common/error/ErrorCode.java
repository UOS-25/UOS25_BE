package com.uos25.uos25.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //Common
    INVALID_INPUT_VALUE("C01", "Invalid Input Value.", HttpStatus.BAD_REQUEST.value()),
    METHOD_NOT_ALLOWED("C02", "Invalid Method Type.", HttpStatus.METHOD_NOT_ALLOWED.value()),
    ENTITY_NOT_FOUND("C03", "Entity Not Found.", HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR("C04", "Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    //Store
    STORE_NOT_FOUND("S01", "User is not Found.", HttpStatus.BAD_REQUEST.value()),
    STORE_ACCESS_DENIED("S02", "User Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    //Funds
    FUNDS_NOT_FOUND("F01", "User is not Found.", HttpStatus.BAD_REQUEST.value()),
    DUPLICATE_PRODUCT_VALUE("P01", "Duplicate product name or code.", HttpStatus.BAD_REQUEST.value()),
    PRODUCT_NOT_FOUND("P02", "Product is not Found", HttpStatus.BAD_REQUEST.value()),
    //Event
    EVENT_NOT_FOUND("E01", "Event is not Found.", HttpStatus.BAD_REQUEST.value());
    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
