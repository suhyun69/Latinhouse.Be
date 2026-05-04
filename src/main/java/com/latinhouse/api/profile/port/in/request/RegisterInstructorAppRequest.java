package com.latinhouse.api.profile.port.in.request;

import com.latinhouse.api.profile.adapter.in.web.request.RegisterInstructorWebRequest;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterInstructorAppRequest {

    String profileId;

    public static RegisterInstructorAppRequest from(RegisterInstructorWebRequest webReq) {
        return RegisterInstructorAppRequest.builder()
                .profileId(webReq.getProfileId())
                .build();
    }
}
