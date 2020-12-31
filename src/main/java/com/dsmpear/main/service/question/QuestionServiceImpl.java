package com.dsmpear.main.service.question;

import com.dsmpear.main.entity.question.Question;
import com.dsmpear.main.entity.question.QuestionRepository;
import com.dsmpear.main.payload.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public void inquiry(QuestionRequest questionRequest) {
        questionRepository.save(
                Question.builder()
                        .email(questionRequest.getEmail())
                        .description(questionRequest.getDescription())
                        .build()
        );
    }

}
