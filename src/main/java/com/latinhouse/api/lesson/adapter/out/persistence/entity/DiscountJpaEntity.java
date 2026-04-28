package com.latinhouse.api.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "lesson_discount")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String type;
    private String condition;
    private BigDecimal amount;
}
