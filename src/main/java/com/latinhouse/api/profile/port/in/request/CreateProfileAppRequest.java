package com.latinhouse.api.profile.port.in.request;

import com.latinhouse.api.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.api.profile.domain.Sex;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProfileAppRequest {

    String nickname;
    Sex sex;
    Boolean isInstructor;

    public static CreateProfileAppRequest from(CreateProfileWebRequest webReq) {
        Sex sex = Sex.of(webReq.getSex());

        boolean isInstructor = webReq.getIsInstructor() != null && webReq.getIsInstructor();

        return CreateProfileAppRequest.builder()
                .nickname(webReq.getNickname())
                .sex(sex)
                .isInstructor(isInstructor)
                .build();
    }
}
