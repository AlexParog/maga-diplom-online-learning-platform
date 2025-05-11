package ru.parog.magauserservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.parog.magauserservice.dto.UserRequestDto;
import ru.parog.magauserservice.dto.UserResponseDto;


public interface UserService {

    Page<UserResponseDto> list(Pageable pageable);

    UserResponseDto getById(Long id);

    UserResponseDto update(Long id, UserRequestDto userDto);

    void delete(Long id);

    UserResponseDto getCurrentUser();
}
