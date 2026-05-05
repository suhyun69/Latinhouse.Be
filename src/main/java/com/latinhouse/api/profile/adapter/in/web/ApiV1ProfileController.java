package com.latinhouse.api.profile.adapter.in.web;

import com.latinhouse.api.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.api.profile.adapter.in.web.request.RegisterInstructorWebRequest;
import com.latinhouse.api.profile.adapter.in.web.request.UpdateProfileWebRequest;
import com.latinhouse.api.profile.adapter.in.web.response.PagedProfileWebResponse;
import com.latinhouse.api.profile.adapter.in.web.response.ProfileWebResponse;
import com.latinhouse.api.profile.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.port.in.DeleteProfileUseCase;
import com.latinhouse.api.profile.port.in.FindProfileUseCase;
import com.latinhouse.api.profile.port.in.UpdateProfileUseCase;
import com.latinhouse.api.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.api.profile.port.in.request.FindProfileAppRequest;
import com.latinhouse.api.profile.port.in.request.RegisterInstructorAppRequest;
import com.latinhouse.api.profile.port.in.request.UpdateProfileAppRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profile", description = "Profile API")
@RequiredArgsConstructor
public class ApiV1ProfileController {

    private final CreateProfileUseCase createProfileUseCase;
    private final FindProfileUseCase findProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;

    @PostMapping
    @Operation(summary = "Create profile")
    public ResponseEntity<ProfileWebResponse> create(@Valid @RequestBody CreateProfileWebRequest webReq) {
        CreateProfileAppRequest appReq = CreateProfileAppRequest.from(webReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProfileWebResponse(createProfileUseCase.create(appReq)));
    }

    @GetMapping
    @Operation(summary = "Find all profiles (paginated)")
    public ResponseEntity<PagedProfileWebResponse> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "isInstructor", required = false) Boolean isInstructor) {
        FindProfileAppRequest searchReq = FindProfileAppRequest.of(nickname, sex, isInstructor);
        return ResponseEntity.ok(new PagedProfileWebResponse(findProfileUseCase.findAll(page, size, searchReq)));
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Find profile by profileId")
    public ResponseEntity<ProfileWebResponse> findById(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(new ProfileWebResponse(findProfileUseCase.findById(profileId)));
    }

    @PutMapping("/{profileId}")
    @Operation(summary = "Update profile (nickname, sex)")
    public ResponseEntity<ProfileWebResponse> update(
            @PathVariable("profileId") String profileId,
            @Valid @RequestBody UpdateProfileWebRequest webReq) {
        UpdateProfileAppRequest appReq = UpdateProfileAppRequest.from(profileId, webReq);
        return ResponseEntity.ok(new ProfileWebResponse(updateProfileUseCase.update(appReq)));
    }

    @PatchMapping("/instructor")
    @Operation(summary = "Register instructor (idempotent)")
    public ResponseEntity<ProfileWebResponse> registerInstructor(
            @Valid @RequestBody RegisterInstructorWebRequest webReq) {
        RegisterInstructorAppRequest appReq = RegisterInstructorAppRequest.from(webReq);
        return ResponseEntity.ok(new ProfileWebResponse(updateProfileUseCase.registerInstructor(appReq)));
    }

    @DeleteMapping("/{profileId}")
    @Operation(summary = "Delete profile")
    public ResponseEntity<Void> delete(@PathVariable("profileId") String profileId) {
        deleteProfileUseCase.deleteById(profileId);
        return ResponseEntity.noContent().build();
    }
}
