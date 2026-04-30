package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.domain.Contact;
import com.latinhouse.api.lesson.domain.Discount;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LessonDetailWebResponse {

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

    public LessonDetailWebResponse(LessonAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.title = appResponse.getTitle();
        this.genre = appResponse.getGenre();
        this.region = appResponse.getRegion();
        this.instructorLo = appResponse.getInstructorLo();
        this.instructorLa = appResponse.getInstructorLa();
        this.startDateTime = appResponse.getStartDateTime();
        this.endDateTime = appResponse.getEndDateTime();
        this.dateTimeSubTexts = appResponse.getDateTimeSubTexts();
        this.place = appResponse.getPlace();
        this.placeUrl = appResponse.getPlaceUrl();
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
