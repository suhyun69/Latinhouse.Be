package com.latinhouse.api.lesson.port.in.response;

import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LessonAppResponse {

    private final Long no;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<LessonOptionAppResponse> options;
    private final BigDecimal price;
    private final BigDecimal maxDiscountAmount;
    private final List<String> discountSubTexts;
    private final String bank;
    private final String accountNumber;
    private final String accountOwner;
    private final List<Discount> discounts;
    private final List<Contact> contacts;

    public LessonAppResponse(Lesson lesson) {
        this.no = lesson.getNo();
        this.title = lesson.getTitle();
        this.genre = lesson.getGenre();
        this.instructorLo = lesson.getInstructorLo();
        this.instructorLa = lesson.getInstructorLa();
        this.options = lesson.getOptions() == null ? List.of() :
                lesson.getOptions().stream().map(LessonOptionAppResponse::new).toList();
        this.price = lesson.getPrice();
        this.maxDiscountAmount = lesson.getMaxDiscountAmount();
        this.discountSubTexts = lesson.getDiscountSubTexts();
        this.bank = lesson.getBank();
        this.accountNumber = lesson.getAccountNumber();
        this.accountOwner = lesson.getAccountOwner();
        this.discounts = lesson.getDiscounts();
        this.contacts = lesson.getContacts();
    }
}
