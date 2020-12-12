package com.dsmpear.main.service.search;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.response.SearchListResponse;
import com.dsmpear.main.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final UserRepository userRepository;

    @Override
    public SearchListResponse getSearchList(String mode, String keyword, Pageable page) {

        List<UserResponse> userResponses = new ArrayList<>();
        Page<User> userPage = null;

        if(mode.equals("profile")){
            userResponses = getProfileList(keyword, page);
            userPage = userPage(keyword, page);
        }

        return SearchListResponse.builder()
                .totalElements(userPage.getNumberOfElements())
                .totalPages(userPage.getTotalPages())
                .userResponses(userResponses)
                .build();



    }

    private List<UserResponse> getProfileList(String keyword, Pageable page){
        Page<User> userPage = userRepository.findAllByNameContains(keyword, page);

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : userPage) {
            userResponses.add(
                    UserResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .build()
            );
        }

        return userResponses;
    }

    private Page<User> userPage(String keyword, Pageable page) {
        Page<User> userPage = userRepository.findAllByNameContains(keyword, page);

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : userPage) {
            userResponses.add(
                    UserResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .build()
            );
        }

        return userPage;
    }

}
