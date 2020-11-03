package com.dsmpear.main.service.question;

import com.dsmpear.main.domain.questions.dto.request.QuestionRequest;

public interface QuestionService {
    void inquiry(QuestionRequest questionRequest);
}
