package com.latinhouse.api.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "lesson")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String title;
    private String genre;
    private String instructorLo;
    private String instructorLa;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonOptionJpaEntity> options;

    private BigDecimal price;
    private BigDecimal maxDiscountAmount;

    @ElementCollection
    @CollectionTable(name = "lesson_discount_sub_text", joinColumns = @JoinColumn(name = "lesson_no"))
    @Column(name = "sub_text")
    private List<String> discountSubTexts;

    private String bank;
    private String accountNumber;
    private String accountOwner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_no")
    private List<DiscountJpaEntity> discounts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_no")
    private List<ContactJpaEntity> contacts;
}
