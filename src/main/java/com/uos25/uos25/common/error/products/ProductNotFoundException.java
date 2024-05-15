package com.uos25.uos25.common.error.products;

import com.uos25.uos25.common.error.EntityNotFoundException;
import com.uos25.uos25.common.error.ErrorCode;


public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
