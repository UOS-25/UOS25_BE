package com.uos25.uos25.common.error.event;

import com.uos25.uos25.common.error.EntityNotFoundException;
import com.uos25.uos25.common.error.ErrorCode;

public class EventNotFoundException extends EntityNotFoundException {
    public EventNotFoundException() {
        super(ErrorCode.EVENT_NOT_FOUND);
    }
}
