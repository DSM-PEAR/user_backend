package com.dsmpear.main.domain.questions.service;

import com.dsmpear.main.domain.questions.dto.request.QuestionRequest;

public interface QuestionService {
    void inquiry(QuestionRequest questionRequest);
}
