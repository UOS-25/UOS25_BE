package com.uos25.uos25.common.error.products;

import com.uos25.uos25.common.error.ErrorCode;
import com.uos25.uos25.common.error.InvalidValueException;

public class DuplicateProductException extends InvalidValueException {

    public DuplicateProductException() {
        super(ErrorCode.DUPLICATE_PRODUCT_VALUE);
    }
}
