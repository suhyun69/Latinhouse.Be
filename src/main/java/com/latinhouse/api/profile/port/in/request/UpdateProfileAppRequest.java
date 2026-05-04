package com.latinhouse.api.profile.port.in.request;

import com.latinhouse.api.profile.adapter.in.web.request.UpdateProfileWebRequest;
import com.latinhouse.api.profile.domain.Sex;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateProfileAppRequest {

    String profileId;
    String nickname;
    Sex sex;

    public static UpdateProfileAppRequest from(String profileId, UpdateProfileWebRequest webReq) {
        Sex sex = Sex.of(webReq.getSex());

        return UpdateProfileAppRequest.builder()
                .profileId(profileId)
                .nickname(webReq.getNickname())
                .sex(sex)
                .build();
    }
}
