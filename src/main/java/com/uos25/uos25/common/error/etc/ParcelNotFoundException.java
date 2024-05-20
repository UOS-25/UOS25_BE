package com.uos25.uos25.common.error.etc;

import com.uos25.uos25.common.error.EntityNotFoundException;
import com.uos25.uos25.common.error.ErrorCode;


public class ParcelNotFoundException extends EntityNotFoundException {

    public ParcelNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
