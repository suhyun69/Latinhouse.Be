package com.latinhouse.api.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lesson_option")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonOptionJpaEntity {

    @Id
    private String id;   // 8-char alphanumeric, set by Service

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_no", nullable = false)
    private LessonJpaEntity lesson;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ElementCollection
    @CollectionTable(name = "lesson_option_datetime_sub_text",
            joinColumns = @JoinColumn(name = "lesson_option_id"))
    @Column(name = "sub_text")
    private List<String> dateTimeSubTexts;

    private String region;
    private String place;
    private String placeUrl;
}
