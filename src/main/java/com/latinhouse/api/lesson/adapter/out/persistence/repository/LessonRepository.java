package com.latinhouse.api.lesson.adapter.out.persistence.repository;

import com.latinhouse.api.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LessonRepository extends JpaRepository<LessonJpaEntity, Long>,
        JpaSpecificationExecutor<LessonJpaEntity> {
    Page<LessonJpaEntity> findAllByOrderByNoDesc(Pageable pageable);
}
