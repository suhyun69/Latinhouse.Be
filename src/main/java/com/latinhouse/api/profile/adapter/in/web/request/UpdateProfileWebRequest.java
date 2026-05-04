package com.latinhouse.api.profile.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProfileWebRequest {

    @NotBlank
    private String nickname;

    @NotNull
    @Pattern(regexp = "^(M|F)$", message = "must be 'M' or 'F'")
    private String sex;
}
