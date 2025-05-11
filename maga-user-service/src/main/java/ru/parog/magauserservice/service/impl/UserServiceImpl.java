package ru.parog.magauserservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import ru.parog.magauserservice.dto.UserRequestDto;
import ru.parog.magauserservice.dto.UserResponseDto;
import ru.parog.magauserservice.entity.User;
import ru.parog.magauserservice.exception.UserNotFoundException;
import ru.parog.magauserservice.mapper.UserMapper;
import ru.parog.magauserservice.repository.UserRepository;
import ru.parog.magauserservice.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponseDto> list(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getById(Long id) {
        User user = findUserOrNotFound(id);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto update(Long id, UserRequestDto userDto) {
        User user = findUserOrNotFound(id);
        userMapper.updateUserFromDto(userDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с ID: {0} не найден", id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getCurrentUser() {
        return userMapper.toDto(findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    private User findUserOrNotFound(Long id) {
        log.debug("Поиск пользователя с ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Пользователь с ID: {} не найден", id);
                    return new UserNotFoundException("Пользователь c id={0} не найден", id);
                });
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с email={0} не найден", email));
    }
}
