package com.gbsw.template.global.security.service;

import com.gbsw.template.domain.user.entity.UserEntity;
import com.gbsw.template.domain.user.repository.UserRepository;
import com.gbsw.template.global.exception.CustomException;
import com.gbsw.template.global.exception.ErrorCode;
import com.gbsw.template.global.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new CustomUserPrincipal(user.getId(), user.getUsername(), user.getPassword());
    }

    public UserDetails loadUserByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new CustomUserPrincipal(user.getId(), user.getUsername(), user.getPassword());
    }
}
