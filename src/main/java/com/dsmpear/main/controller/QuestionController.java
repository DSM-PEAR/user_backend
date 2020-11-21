package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.QuestionRequest;
import com.dsmpear.main.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public void inquiry(@RequestBody @Valid QuestionRequest questionRequest){
        questionService.inquiry(questionRequest);
    }
}
