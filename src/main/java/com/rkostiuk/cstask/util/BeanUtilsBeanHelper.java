package com.rkostiuk.cstask.util;

import com.rkostiuk.cstask.exception.BeanHelperException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class BeanUtilsBeanHelper implements BeanHelper {
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public BeanUtilsBeanHelper(NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    @Override
    public void copyNonNullProperties(Object destination, Object source) throws BeanHelperException {
        try {
            nullAwareBeanUtilsBean.copyProperties(destination, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanHelperException(e);
        }
    }
}
