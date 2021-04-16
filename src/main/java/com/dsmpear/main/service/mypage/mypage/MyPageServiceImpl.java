package com.dsmpear.main.service.mypage.mypage;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.PermissionDeniedException;
import com.dsmpear.main.exceptions.UserNotAccessibleException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfilePageResponse getMyPage() {
        if(!authenticationFacade.isLogin()) {
            throw new PermissionDeniedException();
        }

        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        return ProfilePageResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .selfIntro(user.getSelfIntro())
                .gitHub(user.getGitHub())
                .build();
    }

    @Override
    @Transactional
    public void setSelfIntro(String intro, String gitHub) {
        if(!authenticationFacade.isLogin()) {
            throw new PermissionDeniedException();
        }

        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotAccessibleException::new);

        user.setSelfIntro(intro);
        user.setGitHub(gitHub);
    }

}
