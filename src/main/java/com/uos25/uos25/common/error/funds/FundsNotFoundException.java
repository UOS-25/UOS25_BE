package com.uos25.uos25.common.error.funds;

import com.uos25.uos25.common.error.EntityNotFoundException;
import com.uos25.uos25.common.error.ErrorCode;

public class FundsNotFoundException extends EntityNotFoundException {
    public FundsNotFoundException() {
        super(ErrorCode.FUNDS_NOT_FOUND);
    }
}
