package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.port.in.response.PagedLessonAppResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PagedLessonWebResponse {

    private final List<LessonWebResponse> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;

    public PagedLessonWebResponse(PagedLessonAppResponse appResponse) {
        this.content = appResponse.getContent().stream()
                .map(LessonWebResponse::new)
                .toList();
        this.page = appResponse.getPage();
        this.size = appResponse.getSize();
        this.totalElements = appResponse.getTotalElements();
        this.totalPages = appResponse.getTotalPages();
        this.first = appResponse.isFirst();
        this.last = appResponse.isLast();
    }
}
