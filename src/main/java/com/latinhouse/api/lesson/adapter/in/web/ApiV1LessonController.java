package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.adapter.in.web.request.CreateLessonWebRequest;
import com.latinhouse.api.lesson.adapter.in.web.request.UpdateLessonWebRequest;
import com.latinhouse.api.lesson.adapter.in.web.response.LessonDetailWebResponse;
import com.latinhouse.api.lesson.adapter.in.web.response.LessonWebResponse;
import com.latinhouse.api.lesson.adapter.in.web.response.PagedLessonWebResponse;
import com.latinhouse.api.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.port.in.DeleteLessonUseCase;
import com.latinhouse.api.lesson.port.in.FindLessonUseCase;
import com.latinhouse.api.lesson.port.in.UpdateLessonUseCase;
import com.latinhouse.api.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.request.UpdateLessonAppRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lessons")
@Tag(name = "Lesson", description = "Lesson API")
@RequiredArgsConstructor
public class ApiV1LessonController {

    private final CreateLessonUseCase createLessonUseCase;
    private final FindLessonUseCase findLessonUseCase;
    private final UpdateLessonUseCase updateLessonUseCase;
    private final DeleteLessonUseCase deleteLessonUseCase;

    @PostMapping
    @Operation(summary = "Create lesson")
    public ResponseEntity<LessonDetailWebResponse> create(@Valid @RequestBody CreateLessonWebRequest webReq) {
        CreateLessonAppRequest appReq = CreateLessonAppRequest.from(webReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LessonDetailWebResponse(createLessonUseCase.create(appReq)));
    }

    @GetMapping("/{no}")
    @Operation(summary = "Find lesson by no")
    public ResponseEntity<LessonDetailWebResponse> findByNo(@PathVariable("no") Long no) {
        return ResponseEntity.ok(new LessonDetailWebResponse(findLessonUseCase.findByNo(no)));
    }

    @GetMapping
    @Operation(summary = "Find all lessons (paginated)")
    public ResponseEntity<PagedLessonWebResponse> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(new PagedLessonWebResponse(findLessonUseCase.findAll(page, size)));
    }

    @PutMapping("/{no}")
    @Operation(summary = "Update lesson")
    public ResponseEntity<LessonDetailWebResponse> update(
            @PathVariable("no") Long no,
            @Valid @RequestBody UpdateLessonWebRequest webReq) {
        UpdateLessonAppRequest appReq = UpdateLessonAppRequest.from(webReq);
        return ResponseEntity.ok(new LessonDetailWebResponse(updateLessonUseCase.update(no, appReq)));
    }

    @DeleteMapping("/{no}")
    @Operation(summary = "Delete lesson")
    public ResponseEntity<Void> delete(@PathVariable("no") Long no) {
        deleteLessonUseCase.delete(no);
        return ResponseEntity.noContent().build();
    }
}
