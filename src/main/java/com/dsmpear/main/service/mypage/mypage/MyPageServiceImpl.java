package com.dsmpear.main.service.mypage.mypage;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.UserNotAccessibleException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.response.MyPageResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public MyPageResponse getMypage(String userEmail) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        return MyPageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .selfIntro(user.getSelfIntro())
                .build();
    }

    @Override
    public void setSelfIntro(String userEmail, String intro) {

        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotAccessibleException::new);

        user.setSelfIntro(intro);

    }
}
