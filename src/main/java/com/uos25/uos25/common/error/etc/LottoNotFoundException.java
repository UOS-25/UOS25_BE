package com.uos25.uos25.common.error.etc;

import com.uos25.uos25.common.error.EntityNotFoundException;
import com.uos25.uos25.common.error.ErrorCode;


public class LottoNotFoundException extends EntityNotFoundException {

    public LottoNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
