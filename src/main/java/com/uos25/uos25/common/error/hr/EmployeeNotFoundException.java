package com.uos25.uos25.common.error.hr;

import com.uos25.uos25.common.error.EntityNotFoundException;
import com.uos25.uos25.common.error.ErrorCode;

public class EmployeeNotFoundException extends EntityNotFoundException {
    public EmployeeNotFoundException() {
        super(ErrorCode.EMPLOYEE_NOT_FOUND);
    }
}
