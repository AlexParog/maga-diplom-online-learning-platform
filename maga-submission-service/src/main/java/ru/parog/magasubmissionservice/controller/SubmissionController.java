package ru.parog.magasubmissionservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magasubmissionservice.dto.SubmissionRequestDto;
import ru.parog.magasubmissionservice.dto.SubmissionResponseDto;
import ru.parog.magasubmissionservice.service.SubmissionService;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/{testId}")
    public SubmissionResponseDto startTest(
            @PathVariable Long testId,
            @RequestParam Long userId) {

        return submissionService.startTest(testId, userId);
    }

    @PutMapping("/{id}")
    public SubmissionResponseDto updateAnswers(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody @Valid SubmissionRequestDto request) {

        return submissionService.updateAnswers(id, userId, request);
    }

    @PostMapping("/{id}/complete")
    public SubmissionResponseDto completeTest(
            @PathVariable Long id,
            @RequestParam Long userId) {

        return submissionService.completeTest(id, userId);
    }

    @GetMapping("/{id}")
    public SubmissionResponseDto getSubmissionById(
            @PathVariable Long id,
            @RequestParam Long userId) {

        return submissionService.getSubmissionById(id, userId);
    }
}
