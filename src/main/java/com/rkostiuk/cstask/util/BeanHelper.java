package com.rkostiuk.cstask.util;

import com.rkostiuk.cstask.exception.BeanHelperException;

public interface BeanHelper {
    void copyNonNullProperties(Object destination, Object source) throws BeanHelperException;
}
