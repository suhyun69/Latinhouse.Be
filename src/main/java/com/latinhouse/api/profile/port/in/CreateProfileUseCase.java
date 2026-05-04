package com.latinhouse.api.profile.port.in;

import com.latinhouse.api.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.api.profile.port.in.response.ProfileAppResponse;

public interface CreateProfileUseCase {
    ProfileAppResponse create(CreateProfileAppRequest appReq);
}
