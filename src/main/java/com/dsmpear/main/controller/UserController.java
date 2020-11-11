package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.RegisterRequest;
import com.dsmpear.main.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);
    }
}
