package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.RegisterRequest;
import com.dsmpear.main.payload.response.UserListResponse;
import com.dsmpear.main.service.user.UserListService;
import com.dsmpear.main.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserListService userListService;

    @PostMapping
    public void register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);
    }

    @GetMapping
    public UserListResponse getUserList(@RequestParam String name, Pageable page) {
        return userListService.getUserList(name, page);
    }
}
