package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import com.latinhouse.api.lesson.adapter.out.persistence.mapper.LessonMapper;
import com.latinhouse.api.lesson.adapter.out.persistence.repository.LessonRepository;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.port.in.request.FindLessonAppRequest;
import com.latinhouse.api.lesson.port.out.CreateLessonPort;
import com.latinhouse.api.lesson.port.out.DeleteLessonPort;
import com.latinhouse.api.lesson.port.out.ReadLessonPort;
import com.latinhouse.api.lesson.port.out.UpdateLessonPort;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LessonPersistenceAdapter implements
        CreateLessonPort,
        ReadLessonPort,
        UpdateLessonPort,
        DeleteLessonPort {

    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;

    @Override
    public Lesson create(Lesson lesson) {
        return lessonMapper.mapToDomainEntity(
                lessonRepository.save(lessonMapper.mapToJpaEntity(lesson)));
    }

    @Override
    public Optional<Lesson> findByNo(Long no) {
        return lessonRepository.findById(no)
                .map(lessonMapper::mapToDomainEntity);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::mapToDomainEntity)
                .toList();
    }

    @Override
    public Page<Lesson> findAll(Pageable pageable, FindLessonAppRequest searchReq) {
        Specification<LessonJpaEntity> spec = buildSpecification(searchReq);
        return lessonRepository.findAll(spec, pageable)
                .map(lessonMapper::mapToDomainEntity);
    }

    @Override
    public Lesson update(Lesson lesson) {
        return lessonMapper.mapToDomainEntity(
                lessonRepository.save(lessonMapper.mapToJpaEntity(lesson)));
    }

    @Override
    public void delete(Long no) {
        lessonRepository.deleteById(no);
    }

    /**
     * FindLessonAppRequest의 조건을 JPA Specification으로 변환한다.
     * null 필드는 조건에서 제외한다.
     *
     * instructor 조건: instructorLo 또는 instructorLa 중 하나라도 일치(대소문자 무시 포함)하면 반환.
     *
     * status 조건 (Lesson 자체의 startDateTime/endDateTime 기준):
     *   stand_by  : startDateTime > now
     *   in_progress: startDateTime <= now AND endDateTime >= now
     *   done      : endDateTime < now
     */
    private Specification<LessonJpaEntity> buildSpecification(FindLessonAppRequest searchReq) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchReq.getGenre() != null) {
                predicates.add(cb.equal(root.get("genre"), searchReq.getGenre().getCode()));
            }

            if (searchReq.getRegion() != null) {
                predicates.add(cb.equal(root.get("region"), searchReq.getRegion().getCode()));
            }

            if (searchReq.getInstructor() != null) {
                String pattern = "%" + searchReq.getInstructor().toLowerCase() + "%";
                Predicate loMatch = cb.like(cb.lower(root.get("instructorLo")), pattern);
                Predicate laMatch = cb.like(cb.lower(root.get("instructorLa")), pattern);
                predicates.add(cb.or(loMatch, laMatch));
            }

            if (searchReq.getStatus() != null) {
                LocalDateTime now = LocalDateTime.now();
                switch (searchReq.getStatus()) {
                    case stand_by ->
                            predicates.add(cb.greaterThan(root.get("startDateTime"), now));
                    case in_progress ->
                            predicates.add(cb.and(
                                    cb.lessThanOrEqualTo(root.get("startDateTime"), now),
                                    cb.greaterThanOrEqualTo(root.get("endDateTime"), now)
                            ));
                    case done ->
                            predicates.add(cb.lessThan(root.get("endDateTime"), now));
                }
            }

            // 기본 정렬: no DESC (Pageable의 Sort 설정을 그대로 사용)
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

