package com.dsmpear.main.service.user;

import com.dsmpear.main.payload.request.RegisterRequest;

public interface UserService {
    public void register(RegisterRequest request);
    public void verify(int number, String email);
}
