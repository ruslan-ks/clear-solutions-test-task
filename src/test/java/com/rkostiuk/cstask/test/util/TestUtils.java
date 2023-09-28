package com.rkostiuk.cstask.test.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TestUtils {

    /**
     * @param pageNumber number of current page
     * @param content page content; content.size() is used to set pageSize of the pageable
     * @return PageData containing Pageable and Page
     */
    public static <T> PageData<T> createPageData(int pageNumber, List<T> content) {
        Pageable pageable = PageRequest.of(pageNumber, content.size());
        Page<T> page = TestUtils.createPage(pageable, content);
        return new PageData<>(pageable, page);
    }

    /**
     * Same as {@link TestUtils#createPageData(int, List)}, but pageNumber is set to zero
     */
    public static <T> PageData<T> createPageData(List<T> content) {
        return createPageData(0, content);
    }

    public static <T> Page<T> createPage(Pageable pageable, List<T> list) {
        return new PageImpl<>(list, pageable, list.size());
    }
}
