package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.payload.response.ProfileReportListResponse;
import org.springframework.data.domain.Pageable;

public interface ProfileReportListService {
    ProfileReportListResponse getReport(String email, Pageable page);
}
