package com.dsmpear.main.service.user;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.UserNotAccessibleException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.response.UserListResponse;
import com.dsmpear.main.payload.response.UserResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserListServiceImpl implements UserListService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public UserListResponse getUserList(String name, Pageable page) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotAccessibleException::new);

        Page<User> userPage = userRepository.findAllByNameContainsOrderByName(name, page); // select * from user where name like ='%%'

        List<UserResponse> userResponse = new ArrayList<>();

        for (User user : userPage) {
            userResponse.add(
                    UserResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .build()
            );
        }

        return UserListResponse.builder()
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .userResponses(userResponse)
                .build();
    }

}
