package com.latinhouse.api.profile.port.in;

import com.latinhouse.api.profile.port.in.request.FindProfileAppRequest;
import com.latinhouse.api.profile.port.in.response.PagedProfileAppResponse;
import com.latinhouse.api.profile.port.in.response.ProfileAppResponse;

public interface FindProfileUseCase {
    ProfileAppResponse findById(String profileId);
    PagedProfileAppResponse findAll(int page, int size, FindProfileAppRequest searchReq);
}
