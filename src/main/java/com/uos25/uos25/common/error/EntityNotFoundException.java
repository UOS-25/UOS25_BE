package com.uos25.uos25.common.error;

public class EntityNotFoundException extends BusinessException {
    
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
