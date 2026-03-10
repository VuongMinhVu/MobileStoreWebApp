package com.MobileStore.demo.service;

import com.MobileStore.demo.dto.UserRegistrationDto;
import com.MobileStore.demo.entity.User;
import com.MobileStore.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(UserRegistrationDto registrationDto) {
        // Kiểm tra email tồn tại
        if(userRepository.findByEmail(registrationDto.getEmail()).isPresent()){
            throw new RuntimeException("Email đã tồn tại!");
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFullName(registrationDto.getFullName());

        userRepository.save(user);
    }
}