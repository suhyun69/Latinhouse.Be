package com.latinhouse.api.lesson.port.in.request;

import com.latinhouse.api.lesson.adapter.in.web.request.CreateLessonWebRequest;
import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Value
@Builder
public class CreateLessonAppRequest {

    String title;
    Genre genre;
    Region region;
    String instructorLo;
    String instructorLa;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    List<String> dateTimeSubTexts;
    String place;
    String placeUrl;
    BigDecimal price;
    BigDecimal maxDiscountAmount;
    List<String> discountSubTexts;
    String bank;
    String accountNumber;
    String accountOwner;
    List<Discount> discounts;
    List<Contact> contacts;

    public static CreateLessonAppRequest from(CreateLessonWebRequest webReq) {
        boolean loBlank = webReq.getInstructorLo() == null || webReq.getInstructorLo().isBlank();
        boolean laBlank = webReq.getInstructorLa() == null || webReq.getInstructorLa().isBlank();
        if (loBlank && laBlank) {
            throw new IllegalArgumentException("instructorLo 또는 instructorLa 중 하나는 필수입니다");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = LocalDateTime.parse(webReq.getOption().getStartDate() + " " + webReq.getOption().getStartTime(), formatter);
            endDateTime = LocalDateTime.parse(webReq.getOption().getEndDate() + " " + webReq.getOption().getEndTime(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format.");
        }

        List<Discount> discounts = webReq.getDiscounts() == null ? List.of() :
                webReq.getDiscounts().stream()
                        .map(d -> Discount.builder()
                                .type(DiscountType.of(d.getType()))
                                .condition(d.getCondition())
                                .amount(d.getAmount())
                                .build())
                        .toList();

        List<Contact> contacts = webReq.getContacts() == null ? List.of() :
                webReq.getContacts().stream()
                        .map(c -> Contact.builder()
                                .type(ContactType.of(c.getType()))
                                .name(c.getName())
                                .address(c.getAddress())
                                .build())
                        .toList();


        return CreateLessonAppRequest.builder()
                .title(webReq.getTitle())
                .genre(Genre.of(webReq.getGenre()))
                .region(Region.of(webReq.getOption().getRegion()))
                .instructorLo(webReq.getInstructorLo())
                .instructorLa(webReq.getInstructorLa())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .dateTimeSubTexts(webReq.getDateTimeSubTexts())
                .place(webReq.getOption().getPlace())
                .placeUrl(webReq.getOption().getPlaceUrl())
                .price(webReq.getPrice())
                .maxDiscountAmount(webReq.getMaxDiscountAmount())
                .discountSubTexts(webReq.getDiscountSubTexts())
                .bank(webReq.getBank())
                .accountNumber(webReq.getAccountNumber())
                .accountOwner(webReq.getAccountOwner())
                .discounts(discounts)
                .contacts(contacts)
                .build();
    }
}
