package com.latinhouse.api.lesson.port.in.response;

import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LessonAppResponse {

    private final Long no;
    private final String title;
    private final Genre genre;
    private final Region region;
    private final String instructorLo;
    private final String instructorLa;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> dateTimeSubTexts;
    private final String place;
    private final String placeUrl;
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
        this.region = lesson.getRegion();
        this.instructorLo = lesson.getInstructorLo();
        this.instructorLa = lesson.getInstructorLa();
        this.startDateTime = lesson.getStartDateTime();
        this.endDateTime = lesson.getEndDateTime();
        this.dateTimeSubTexts = lesson.getDateTimeSubTexts();
        this.place = lesson.getPlace();
        this.placeUrl = lesson.getPlaceUrl();
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
