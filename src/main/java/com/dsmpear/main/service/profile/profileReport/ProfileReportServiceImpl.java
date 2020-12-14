package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileReportServiceImpl implements ProfileReportService{
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfileReportListResponse getReport(String userEmail, Pageable page) {
        return null;
    }
}
