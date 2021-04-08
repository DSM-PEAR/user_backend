package com.dsmpear.main.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileReportListResponse extends PageResponse{

    private List<ProfileReportResponse> profileReportResponses;
    private List<MyPageReportResponse> myPageReportResponses;

    @Builder
    public ProfileReportListResponse(Long totalElements, int totalPages, List<ProfileReportResponse> profileReportResponses, List<MyPageReportResponse> myProfileReportResponses) {
        super(totalElements, totalPages);
        this.profileReportResponses = profileReportResponses;
        this.myPageReportResponses = myProfileReportResponses;
    }

}
