package com.gbsw.template.domain.auth.controller;

import com.gbsw.template.domain.auth.dto.LoginRequest;
import com.gbsw.template.domain.auth.dto.RefreshRequest;
import com.gbsw.template.domain.auth.dto.SignUpRequest;
import com.gbsw.template.domain.auth.dto.TokenResponse;
import com.gbsw.template.domain.auth.service.AuthService;
import com.gbsw.template.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ApiResponse.success(authService.refresh(request.getRefreshToken()));
    }
}
