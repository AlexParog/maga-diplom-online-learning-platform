package ru.parog.magatestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magatestservice.dto.QuestionRequestDto;
import ru.parog.magatestservice.dto.QuestionResponseDto;
import ru.parog.magatestservice.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{id}")
    public QuestionResponseDto getById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PutMapping("/{id}")
    public QuestionResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid QuestionRequestDto request) {

        return questionService.updateQuestion(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}