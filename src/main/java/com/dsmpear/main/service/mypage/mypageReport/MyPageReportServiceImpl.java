package com.dsmpear.main.service.mypage.mypageReport;

import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageReportServiceImpl implements MyPageReportService{

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfileReportListResponse getReport(Pageable page) {
        return null;
    }
}
