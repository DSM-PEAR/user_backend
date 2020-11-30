package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.payload.response.ApplicationListResponse;
import org.springframework.data.domain.Pageable;

public interface ProfileReportListService {
    ApplicationListResponse getReport(String email, Pageable page);
}
