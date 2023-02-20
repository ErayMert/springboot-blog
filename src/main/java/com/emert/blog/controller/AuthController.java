package com.emert.blog.controller;


import com.emert.blog.payload.base.BaseResponse;
import com.emert.blog.payload.request.LoginRequest;
import com.emert.blog.payload.request.RegisterRequest;
import com.emert.blog.payload.response.AuthResponse;
import com.emert.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<BaseResponse<AuthResponse>> login(@RequestBody LoginRequest request){
        String token = authService.login(request);
        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(token)
                .build();
        return ResponseEntity.ok(new BaseResponse<>(authResponse));
    }

    @PostMapping(value = {"/register", "/sign-up"})
    public ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterRequest request){
        String response = authService.register(request);
        return new ResponseEntity<>(new BaseResponse<>(response), HttpStatus.CREATED);
    }

}
