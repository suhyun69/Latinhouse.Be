package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LessonDetailWebResponse {

    private final Long no;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<LessonOptionWebResponse> options;
    private final BigDecimal price;
    private final BigDecimal maxDiscountAmount;
    private final List<String> discountSubTexts;
    private final String bank;
    private final String accountNumber;
    private final String accountOwner;
    private final List<Discount> discounts;
    private final List<Contact> contacts;

    public LessonDetailWebResponse(LessonAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.title = appResponse.getTitle();
        this.genre = appResponse.getGenre();
        this.instructorLo = appResponse.getInstructorLo();
        this.instructorLa = appResponse.getInstructorLa();
        this.options = appResponse.getOptions() == null ? List.of() :
                appResponse.getOptions().stream().map(LessonOptionWebResponse::new).toList();
        this.price = appResponse.getPrice();
        this.maxDiscountAmount = appResponse.getMaxDiscountAmount();
        this.discountSubTexts = appResponse.getDiscountSubTexts();
        this.bank = appResponse.getBank();
        this.accountNumber = appResponse.getAccountNumber();
        this.accountOwner = appResponse.getAccountOwner();
        this.discounts = appResponse.getDiscounts();
        this.contacts = appResponse.getContacts();
    }
}
