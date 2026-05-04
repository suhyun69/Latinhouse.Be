package com.latinhouse.api.profile.adapter.in.web.response;

import com.latinhouse.api.profile.port.in.response.PagedProfileAppResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PagedProfileWebResponse {

    private final List<ProfileWebResponse> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;

    public PagedProfileWebResponse(PagedProfileAppResponse appResponse) {
        this.content = appResponse.getContent().stream()
                .map(ProfileWebResponse::new)
                .toList();
        this.page = appResponse.getPage();
        this.size = appResponse.getSize();
        this.totalElements = appResponse.getTotalElements();
        this.totalPages = appResponse.getTotalPages();
        this.first = appResponse.isFirst();
        this.last = appResponse.isLast();
    }
}
