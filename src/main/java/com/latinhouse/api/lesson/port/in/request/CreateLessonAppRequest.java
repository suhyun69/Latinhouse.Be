package com.latinhouse.api.lesson.port.in.request;

import com.latinhouse.api.lesson.adapter.in.web.request.CreateLessonWebRequest;
import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class CreateLessonAppRequest {

    String title;
    Genre genre;
    String instructorLo;
    String instructorLa;
    List<LessonOptionAppRequest> options;
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

        if (webReq.getOptions() == null || webReq.getOptions().isEmpty()) {
            throw new IllegalArgumentException("options는 최소 1개 이상 필요합니다.");
        }

        List<LessonOptionAppRequest> options = webReq.getOptions().stream()
                .map(LessonOptionAppRequest::from)
                .toList();

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
                .instructorLo(webReq.getInstructorLo())
                .instructorLa(webReq.getInstructorLa())
                .options(options)
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
