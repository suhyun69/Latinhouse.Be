package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.global.exception.CustomException;
import com.latinhouse.api.global.exception.ErrorCode;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.port.in.DeleteLessonUseCase;
import com.latinhouse.api.lesson.port.in.FindLessonUseCase;
import com.latinhouse.api.lesson.port.in.UpdateLessonUseCase;
import com.latinhouse.api.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.request.FindLessonAppRequest;
import com.latinhouse.api.lesson.port.in.request.UpdateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import com.latinhouse.api.lesson.port.in.response.PagedLessonAppResponse;
import com.latinhouse.api.lesson.port.out.CreateLessonPort;
import com.latinhouse.api.lesson.port.out.DeleteLessonPort;
import com.latinhouse.api.lesson.port.out.ReadLessonPort;
import com.latinhouse.api.lesson.port.out.UpdateLessonPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService implements
        CreateLessonUseCase,
        FindLessonUseCase,
        UpdateLessonUseCase,
        DeleteLessonUseCase {

    private final CreateLessonPort createLessonPort;
    private final ReadLessonPort readLessonPort;
    private final UpdateLessonPort updateLessonPort;
    private final DeleteLessonPort deleteLessonPort;

    @Override
    public LessonAppResponse create(CreateLessonAppRequest appReq) {
        Lesson lesson = Lesson.builder()
                .title(appReq.getTitle())
                .genre(appReq.getGenre())
                .region(appReq.getRegion())
                .instructorLo(appReq.getInstructorLo())
                .instructorLa(appReq.getInstructorLa())
                .startDateTime(appReq.getStartDateTime())
                .endDateTime(appReq.getEndDateTime())
                .dateTimeSubTexts(appReq.getDateTimeSubTexts())
                .place(appReq.getPlace())
                .placeUrl(appReq.getPlaceUrl())
                .price(appReq.getPrice())
                .maxDiscountAmount(appReq.getMaxDiscountAmount())
                .discountSubTexts(appReq.getDiscountSubTexts())
                .bank(appReq.getBank())
                .accountNumber(appReq.getAccountNumber())
                .accountOwner(appReq.getAccountOwner())
                .discounts(appReq.getDiscounts())
                .contacts(appReq.getContacts())
                .build();

        return new LessonAppResponse(createLessonPort.create(lesson));
    }

    @Override
    public LessonAppResponse findByNo(Long no) {
        return readLessonPort.findByNo(no)
                .map(LessonAppResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));
    }

    @Override
    public List<LessonAppResponse> findAll() {
        return readLessonPort.findAll().stream()
                .map(LessonAppResponse::new)
                .toList();
    }

    @Override
    public PagedLessonAppResponse findAll(int page, int size, FindLessonAppRequest searchReq) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "no"));
        return new PagedLessonAppResponse(
                readLessonPort.findAll(pageRequest, searchReq).map(LessonAppResponse::new));
    }

    @Override
    public LessonAppResponse update(Long no, UpdateLessonAppRequest appReq) {
        Lesson existing = readLessonPort.findByNo(no)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));

        Lesson updated = Lesson.builder()
                .no(existing.getNo())
                .title(appReq.getTitle())
                .genre(appReq.getGenre())
                .region(appReq.getRegion())
                .instructorLo(appReq.getInstructorLo())
                .instructorLa(appReq.getInstructorLa())
                .startDateTime(appReq.getStartDateTime())
                .endDateTime(appReq.getEndDateTime())
                .dateTimeSubTexts(appReq.getDateTimeSubTexts())
                .place(appReq.getPlace())
                .placeUrl(appReq.getPlaceUrl())
                .price(appReq.getPrice())
                .maxDiscountAmount(appReq.getMaxDiscountAmount())
                .discountSubTexts(appReq.getDiscountSubTexts())
                .bank(appReq.getBank())
                .accountNumber(appReq.getAccountNumber())
                .accountOwner(appReq.getAccountOwner())
                .discounts(appReq.getDiscounts())
                .contacts(appReq.getContacts())
                .build();

        return new LessonAppResponse(updateLessonPort.update(updated));
    }

    @Override
    public void delete(Long no) {
        readLessonPort.findByNo(no)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));
        deleteLessonPort.delete(no);
    }
}
