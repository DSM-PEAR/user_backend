package com.dsmpear.main.service.search;

import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.payload.response.SearchProfileResponse;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    SearchProfileResponse searchProfile(String keyword, Pageable page);

    ReportListResponse searchReportByTitle(Pageable page, String title);
}
