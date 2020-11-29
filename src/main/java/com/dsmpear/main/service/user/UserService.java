package com.dsmpear.main.service.user;

import com.dsmpear.main.payload.request.RegisterRequest;

public interface UserService {

    void register(RegisterRequest request);
    void verify(String number, String email);

}
