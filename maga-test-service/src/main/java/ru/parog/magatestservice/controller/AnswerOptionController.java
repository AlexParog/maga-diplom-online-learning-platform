package ru.parog.magatestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magatestservice.dto.AnswerOptionRequestDto;
import ru.parog.magatestservice.dto.AnswerOptionResponseDto;
import ru.parog.magatestservice.service.AnswerOptionService;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class AnswerOptionController {

    private final AnswerOptionService answerOptionService;

    @GetMapping("/{id}")
    public AnswerOptionResponseDto getById(@PathVariable Long id) {
        return answerOptionService.getOptionById(id);
    }

    @PutMapping("/{id}")
    public AnswerOptionResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid AnswerOptionRequestDto request) {

        return answerOptionService.updateOption(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        answerOptionService.deleteOption(id);
    }
}
