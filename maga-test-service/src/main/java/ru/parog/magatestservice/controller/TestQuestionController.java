package ru.parog.magatestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magatestservice.dto.QuestionCreateRequest;
import ru.parog.magatestservice.dto.QuestionResponseDto;
import ru.parog.magatestservice.service.QuestionService;

@RestController
@RequestMapping("/api/tests/{testId}/questions")
@RequiredArgsConstructor
public class TestQuestionController {

    private final QuestionService questionService;

    @PostMapping
    public QuestionResponseDto createQuestion(
            @RequestBody @Valid QuestionCreateRequest request) {

        return questionService.createQuestion(request);
    }
}
