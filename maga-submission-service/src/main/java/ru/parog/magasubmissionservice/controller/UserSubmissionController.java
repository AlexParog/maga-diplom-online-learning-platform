package ru.parog.magasubmissionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magasubmissionservice.dto.SubmissionResponseDto;
import ru.parog.magasubmissionservice.service.SubmissionService;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/submissions")
@RequiredArgsConstructor
public class UserSubmissionController {

    private final SubmissionService submissionService;

    @GetMapping
    public List<SubmissionResponseDto> getSubmissionsByUserId(@PathVariable Long userId) {
        return submissionService.getUserSubmissions(userId);
    }
}
