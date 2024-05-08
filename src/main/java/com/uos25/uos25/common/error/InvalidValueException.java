package com.uos25.uos25.common.error;

public class InvalidValueException extends BusinessException {
    
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
