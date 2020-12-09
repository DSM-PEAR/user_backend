package com.dsmpear.main.service.user;

import com.dsmpear.main.payload.response.UserListResponse;
import org.springframework.data.domain.Pageable;

public interface UserListService {
    UserListResponse getUserList(String name, Pageable page);
}
