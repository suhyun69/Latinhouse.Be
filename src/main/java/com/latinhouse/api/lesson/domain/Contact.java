package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Contact {
    private final Long id;
    private final ContactType type;    // "P", "K", "I", "Y", "W"
    private final String name;    // 담당자명 또는 별명
    private final String address; // 연락처, 링크, 계정명 등
}