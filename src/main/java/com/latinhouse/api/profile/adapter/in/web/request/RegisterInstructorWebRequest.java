package com.latinhouse.api.profile.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterInstructorWebRequest {

    @NotBlank
    private String profileId;
}
