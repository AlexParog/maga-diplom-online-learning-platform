package ru.parog.magatestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magatestservice.dto.TestCreateRequest;
import ru.parog.magatestservice.dto.TestRequestDto;
import ru.parog.magatestservice.dto.TestResponseDto;
import ru.parog.magatestservice.service.TestService;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping
    public TestResponseDto create(@RequestBody @Valid TestCreateRequest request) {
        return testService.createTest(request);
    }

    @GetMapping("/{id}")
    public TestResponseDto getById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

    @PutMapping("/{id}")
    public TestResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid TestRequestDto request) {

        return testService.updateTest(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testService.deleteTest(id);
    }
}
