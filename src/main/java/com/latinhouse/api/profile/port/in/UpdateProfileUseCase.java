package com.latinhouse.api.profile.port.in;

import com.latinhouse.api.profile.port.in.request.RegisterInstructorAppRequest;
import com.latinhouse.api.profile.port.in.request.UpdateProfileAppRequest;
import com.latinhouse.api.profile.port.in.response.ProfileAppResponse;

public interface UpdateProfileUseCase {
    ProfileAppResponse update(UpdateProfileAppRequest appReq);
    ProfileAppResponse registerInstructor(RegisterInstructorAppRequest appReq);
}
