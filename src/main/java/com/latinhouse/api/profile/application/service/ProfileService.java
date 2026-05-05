package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.global.exception.CustomException;
import com.latinhouse.api.global.exception.ErrorCode;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.port.in.DeleteProfileUseCase;
import com.latinhouse.api.profile.port.in.FindProfileUseCase;
import com.latinhouse.api.profile.port.in.UpdateProfileUseCase;
import com.latinhouse.api.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.api.profile.port.in.request.FindProfileAppRequest;
import com.latinhouse.api.profile.port.in.request.RegisterInstructorAppRequest;
import com.latinhouse.api.profile.port.in.request.UpdateProfileAppRequest;
import com.latinhouse.api.profile.port.in.response.PagedProfileAppResponse;
import com.latinhouse.api.profile.port.in.response.ProfileAppResponse;
import com.latinhouse.api.profile.port.out.CreateProfilePort;
import com.latinhouse.api.profile.port.out.DeleteProfilePort;
import com.latinhouse.api.profile.port.out.ReadProfilePort;
import com.latinhouse.api.profile.port.out.UpdateProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ProfileService implements CreateProfileUseCase, FindProfileUseCase, UpdateProfileUseCase, DeleteProfileUseCase {

    private static final String ID_CHARS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
    private static final int ID_LENGTH = 8;
    private static final int MAX_RETRY = 5;

    private final SecureRandom secureRandom = new SecureRandom();

    private final CreateProfilePort createProfilePort;
    private final ReadProfilePort readProfilePort;
    private final UpdateProfilePort updateProfilePort;
    private final DeleteProfilePort deleteProfilePort;

    @Override
    public ProfileAppResponse create(CreateProfileAppRequest appReq) {
        String profileId = generateUniqueProfileId();

        Profile profile = Profile.builder()
                .profileId(profileId)
                .nickname(appReq.getNickname())
                .sex(appReq.getSex())
                .isInstructor(appReq.getIsInstructor())
                .build();

        return new ProfileAppResponse(createProfilePort.create(profile));
    }

    @Override
    public ProfileAppResponse findById(String profileId) {
        return readProfilePort.findById(profileId)
                .map(ProfileAppResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));
    }

    @Override
    public PagedProfileAppResponse findAll(int page, int size, FindProfileAppRequest searchReq) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "profileId"));
        return new PagedProfileAppResponse(
                readProfilePort.findAll(pageRequest, searchReq).map(ProfileAppResponse::new));
    }

    @Override
    public ProfileAppResponse update(UpdateProfileAppRequest appReq) {
        Profile existing = readProfilePort.findById(appReq.getProfileId())
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        Profile updated = Profile.builder()
                .profileId(existing.getProfileId())
                .nickname(appReq.getNickname())
                .sex(appReq.getSex())
                .isInstructor(existing.getIsInstructor())
                .build();

        return new ProfileAppResponse(updateProfilePort.update(updated));
    }

    @Override
    public ProfileAppResponse registerInstructor(RegisterInstructorAppRequest appReq) {
        Profile existing = readProfilePort.findById(appReq.getProfileId())
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        if (Boolean.TRUE.equals(existing.getIsInstructor())) {
            return new ProfileAppResponse(existing);
        }

        Profile updated = Profile.builder()
                .profileId(existing.getProfileId())
                .nickname(existing.getNickname())
                .sex(existing.getSex())
                .isInstructor(true)
                .build();

        return new ProfileAppResponse(updateProfilePort.update(updated));
    }

    @Override
    public void deleteById(String profileId) {
        if (!readProfilePort.existsById(profileId)) {
            throw new CustomException(ErrorCode.PROFILE_NOT_FOUND);
        }
        deleteProfilePort.deleteById(profileId);
    }

    private String generateUniqueProfileId() {
        for (int attempt = 0; attempt < MAX_RETRY; attempt++) {
            String candidate = generateRandomId();
            if (!readProfilePort.existsById(candidate)) {
                return candidate;
            }
        }
        throw new CustomException(ErrorCode.INVALID_REQUEST);
    }

    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(ID_CHARS.charAt(secureRandom.nextInt(ID_CHARS.length())));
        }
        return sb.toString();
    }
}
