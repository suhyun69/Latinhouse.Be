package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.global.exception.CustomException;
import com.latinhouse.api.global.exception.ErrorCode;
import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonOption;
import com.latinhouse.api.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.port.in.DeleteLessonUseCase;
import com.latinhouse.api.lesson.port.in.FindLessonUseCase;
import com.latinhouse.api.lesson.port.in.UpdateLessonUseCase;
import com.latinhouse.api.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.request.FindLessonAppRequest;
import com.latinhouse.api.lesson.port.in.request.LessonOptionAppRequest;
import com.latinhouse.api.lesson.port.in.request.UpdateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import com.latinhouse.api.lesson.port.in.response.PagedLessonAppResponse;
import com.latinhouse.api.lesson.port.out.CreateLessonPort;
import com.latinhouse.api.lesson.port.out.DeleteLessonPort;
import com.latinhouse.api.lesson.port.out.ReadLessonPort;
import com.latinhouse.api.lesson.port.out.UpdateLessonPort;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import com.latinhouse.api.profile.port.out.ReadProfilePort;
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
    private final ReadProfilePort readProfilePort;

    @Override
    public LessonAppResponse create(CreateLessonAppRequest appReq) {
        Profile instructor = resolveInstructor(appReq.getInstructorProfileId());
        List<LessonOption> options = buildOptionsForCreate(appReq.getOptions());

        Lesson lesson = Lesson.builder()
                .title(appReq.getTitle())
                .genre(appReq.getGenre())
                .instructorLo(instructor.getSex() == Sex.M ? instructor.getNickname() : null)
                .instructorLa(instructor.getSex() == Sex.F ? instructor.getNickname() : null)
                .options(options)
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

        Profile instructor = resolveInstructor(appReq.getInstructorProfileId());
        List<LessonOption> options = buildOptionsForUpdate(appReq.getOptions());

        List<Discount> discounts = appReq.getDiscounts() == null ? List.of() :
                appReq.getDiscounts().stream()
                        .map(d -> Discount.builder()
                                .id(d.getId())
                                .type(d.getType())
                                .condition(d.getCondition())
                                .amount(d.getAmount())
                                .build())
                        .toList();

        List<Contact> contacts = appReq.getContacts() == null ? List.of() :
                appReq.getContacts().stream()
                        .map(c -> Contact.builder()
                                .id(c.getId())
                                .type(c.getType())
                                .name(c.getName())
                                .address(c.getAddress())
                                .build())
                        .toList();

        Lesson updated = Lesson.builder()
                .no(existing.getNo())
                .title(appReq.getTitle())
                .genre(appReq.getGenre())
                .instructorLo(instructor.getSex() == Sex.M ? instructor.getNickname() : null)
                .instructorLa(instructor.getSex() == Sex.F ? instructor.getNickname() : null)
                .options(options)
                .price(appReq.getPrice())
                .maxDiscountAmount(appReq.getMaxDiscountAmount())
                .discountSubTexts(appReq.getDiscountSubTexts())
                .bank(appReq.getBank())
                .accountNumber(appReq.getAccountNumber())
                .accountOwner(appReq.getAccountOwner())
                .discounts(discounts)
                .contacts(contacts)
                .build();

        return new LessonAppResponse(updateLessonPort.update(updated));
    }

    @Override
    public void delete(Long no) {
        readLessonPort.findByNo(no)
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));
        deleteLessonPort.delete(no);
    }

    private Profile resolveInstructor(String instructorProfileId) {
        Profile profile = readProfilePort.findById(instructorProfileId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));
        if (!Boolean.TRUE.equals(profile.getIsInstructor())) {
            throw new CustomException(ErrorCode.NOT_AN_INSTRUCTOR);
        }
        return profile;
    }

    private List<LessonOption> buildOptionsForCreate(List<LessonOptionAppRequest> optionRequests) {
        if (optionRequests == null) return List.of();
        return optionRequests.stream()
                .map(req -> LessonOption.builder()
                        .startDateTime(req.getStartDateTime())
                        .endDateTime(req.getEndDateTime())
                        .dateTimeSubTexts(req.getDateTimeSubTexts())
                        .region(req.getRegion())
                        .place(req.getPlace())
                        .placeUrl(req.getPlaceUrl())
                        .build())
                .toList();
    }

    private List<LessonOption> buildOptionsForUpdate(List<LessonOptionAppRequest> optionRequests) {
        if (optionRequests == null) return List.of();
        return optionRequests.stream()
                .map(req -> LessonOption.builder()
                        .no(req.getNo())    // null → 신규(DB 자동채번), 값 있음 → 기존 레코드 재사용
                        .startDateTime(req.getStartDateTime())
                        .endDateTime(req.getEndDateTime())
                        .dateTimeSubTexts(req.getDateTimeSubTexts())
                        .region(req.getRegion())
                        .place(req.getPlace())
                        .placeUrl(req.getPlaceUrl())
                        .build())
                .toList();
    }
}
