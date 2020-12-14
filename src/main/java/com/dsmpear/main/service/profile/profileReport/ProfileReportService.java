package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.payload.response.ProfileReportListResponse;
import org.springframework.data.domain.Pageable;

public interface ProfileReportService {
    ProfileReportListResponse getReport(String userEmail,Pageable page);
}
