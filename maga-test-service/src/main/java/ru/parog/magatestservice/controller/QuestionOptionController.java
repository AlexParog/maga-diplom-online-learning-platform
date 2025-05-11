package ru.parog.magatestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magatestservice.dto.AnswerOptionCreateRequest;
import ru.parog.magatestservice.dto.AnswerOptionResponseDto;
import ru.parog.magatestservice.service.AnswerOptionService;

@RestController
@RequestMapping("/api/questions/{questionId}/options")
@RequiredArgsConstructor
public class QuestionOptionController {

    private final AnswerOptionService answerOptionService;

    @PostMapping
    public AnswerOptionResponseDto createOption(
            @RequestBody @Valid AnswerOptionCreateRequest request) {

        return answerOptionService.createOption(request);
    }
}
