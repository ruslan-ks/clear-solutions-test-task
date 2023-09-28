package com.rkostiuk.cstask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Combines pageable and page objects used together
 */
public record PageData<T>(Pageable pageable, Page<T> page) {
}
