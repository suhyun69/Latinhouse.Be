package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.lesson.adapter.out.persistence.mapper.LessonMapper;
import com.latinhouse.api.lesson.adapter.out.persistence.repository.LessonRepository;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.port.out.CreateLessonPort;
import com.latinhouse.api.lesson.port.out.DeleteLessonPort;
import com.latinhouse.api.lesson.port.out.ReadLessonPort;
import com.latinhouse.api.lesson.port.out.UpdateLessonPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
    public Page<Lesson> findAll(Pageable pageable) {
        return lessonRepository.findAllByOrderByNoDesc(pageable)
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
}
