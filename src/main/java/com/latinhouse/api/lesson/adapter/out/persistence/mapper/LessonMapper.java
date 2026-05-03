package com.latinhouse.api.lesson.adapter.out.persistence.mapper;

import com.latinhouse.api.lesson.adapter.out.persistence.entity.ContactJpaEntity;
import com.latinhouse.api.lesson.adapter.out.persistence.entity.DiscountJpaEntity;
import com.latinhouse.api.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import com.latinhouse.api.lesson.adapter.out.persistence.entity.LessonOptionJpaEntity;
import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonOption;
import com.latinhouse.api.lesson.domain.Region;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonMapper {

    public LessonJpaEntity mapToJpaEntity(Lesson lesson) {
        LessonJpaEntity entity = LessonJpaEntity.builder()
                .no(lesson.getNo())
                .title(lesson.getTitle())
                .genre(lesson.getGenre().getCode())
                .instructorLo(lesson.getInstructorLo())
                .instructorLa(lesson.getInstructorLa())
                .price(lesson.getPrice())
                .maxDiscountAmount(lesson.getMaxDiscountAmount())
                .discountSubTexts(lesson.getDiscountSubTexts())
                .bank(lesson.getBank())
                .accountNumber(lesson.getAccountNumber())
                .accountOwner(lesson.getAccountOwner())
                .discounts(mapToDiscountEntities(lesson.getDiscounts()))
                .contacts(mapToContactEntities(lesson.getContacts()))
                .build();

        List<LessonOptionJpaEntity> optionEntities = mapToOptionEntities(lesson.getOptions(), entity);
        entity.setOptions(optionEntities);

        return entity;
    }

    public Lesson mapToDomainEntity(LessonJpaEntity entity) {
        return Lesson.builder()
                .no(entity.getNo())
                .title(entity.getTitle())
                .genre(Genre.of(entity.getGenre()))
                .instructorLo(entity.getInstructorLo())
                .instructorLa(entity.getInstructorLa())
                .options(mapToOptionDomains(entity.getOptions()))
                .price(entity.getPrice())
                .maxDiscountAmount(entity.getMaxDiscountAmount())
                .discountSubTexts(entity.getDiscountSubTexts())
                .bank(entity.getBank())
                .accountNumber(entity.getAccountNumber())
                .accountOwner(entity.getAccountOwner())
                .discounts(mapToDiscountDomains(entity.getDiscounts()))
                .contacts(mapToContactDomains(entity.getContacts()))
                .build();
    }

    private List<LessonOptionJpaEntity> mapToOptionEntities(List<LessonOption> options, LessonJpaEntity lessonEntity) {
        if (options == null) return List.of();
        return options.stream()
                .map(o -> LessonOptionJpaEntity.builder()
                        .no(o.getNo())
                        .lesson(lessonEntity)
                        .startDateTime(o.getStartDateTime())
                        .endDateTime(o.getEndDateTime())
                        .dateTimeSubTexts(o.getDateTimeSubTexts())
                        .region(o.getRegion().getCode())
                        .place(o.getPlace())
                        .placeUrl(o.getPlaceUrl())
                        .build())
                .toList();
    }

    private List<LessonOption> mapToOptionDomains(List<LessonOptionJpaEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(e -> LessonOption.builder()
                        .no(e.getNo())
                        .startDateTime(e.getStartDateTime())
                        .endDateTime(e.getEndDateTime())
                        .dateTimeSubTexts(e.getDateTimeSubTexts())
                        .region(Region.of(e.getRegion()))
                        .place(e.getPlace())
                        .placeUrl(e.getPlaceUrl())
                        .build())
                .toList();
    }

    private List<DiscountJpaEntity> mapToDiscountEntities(List<Discount> discounts) {
        if (discounts == null) return List.of();
        return discounts.stream()
                .map(d -> DiscountJpaEntity.builder()
                        .no(d.getId())
                        .type(d.getType().getCode())
                        .condition(d.getCondition())
                        .amount(d.getAmount())
                        .build())
                .toList();
    }

    private List<Discount> mapToDiscountDomains(List<DiscountJpaEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(e -> Discount.builder()
                        .id(e.getNo())
                        .type(DiscountType.of(e.getType()))
                        .condition(e.getCondition())
                        .amount(e.getAmount())
                        .build())
                .toList();
    }

    private List<ContactJpaEntity> mapToContactEntities(List<Contact> contacts) {
        if (contacts == null) return List.of();
        return contacts.stream()
                .map(c -> ContactJpaEntity.builder()
                        .no(c.getId())
                        .type(c.getType().getCode())
                        .name(c.getName())
                        .address(c.getAddress())
                        .build())
                .toList();
    }

    private List<Contact> mapToContactDomains(List<ContactJpaEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(e -> Contact.builder()
                        .id(e.getNo())
                        .type(ContactType.of(e.getType()))
                        .name(e.getName())
                        .address(e.getAddress())
                        .build())
                .toList();
    }
}
