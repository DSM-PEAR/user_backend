package com.dsmpear.main.service.search;

import com.dsmpear.main.payload.response.SearchListResponse;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    SearchListResponse getSearchList(String mode, String keyword, Pageable page);

}
