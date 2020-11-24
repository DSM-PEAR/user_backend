package com.dsmpear.main.service.profile.profile;

import com.dsmpear.main.payload.response.ProfilePageResponse;

public interface ProfileService {
    ProfilePageResponse getProfile(String email);
}
