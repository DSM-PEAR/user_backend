package com.dsmpear.main.domain;

import com.dsmpear.main.entity.notice.Notice;
import com.dsmpear.main.entity.notice.NoticeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private NoticeRepository noticeRepository;

    private MockMvc mvc;

    @PostConstruct
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        noticeRepository.save(
                Notice.builder()
                        .title("first")
                        .description("first")
                        .fileName("filename1")
                        .createdAt(LocalDateTime.now())
                .build()
        );

        noticeRepository.save(
                Notice.builder()
                        .title("second")
                        .description("second")
                        .fileName("filename2")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        noticeRepository.save(
                Notice.builder()
                        .title("third")
                        .description("third")
                        .fileName("filename3")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        noticeRepository.save(
                Notice.builder()
                        .title("fourth")
                        .description("fourth")
                        .fileName("filename4")
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Test
    public void  getNoticeList() throws Exception{

        mvc.perform(get("/notice").
                content(new ObjectMapper().writeValueAsString(1))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void  getNoticeContent() throws Exception{

        mvc.perform(get("/notice/3").
                content(new ObjectMapper().writeValueAsString(3))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

}
