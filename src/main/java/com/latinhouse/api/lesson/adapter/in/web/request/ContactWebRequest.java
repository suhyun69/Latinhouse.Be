package com.latinhouse.api.lesson.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactWebRequest {

    @NotBlank
    @Pattern(regexp = "^(P|K|I|Y|W)$", message = "must be 'P', 'K', 'I', 'Y', or 'W'")
    private String type;

    private String name;

    @NotBlank
    private String address;
}
