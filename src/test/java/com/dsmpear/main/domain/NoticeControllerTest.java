package com.dsmpear.main.domain;

import com.dsmpear.main.entity.notice.Notice;
import com.dsmpear.main.entity.notice.NoticeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NoticeControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private NoticeRepository noticeRepository;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @After
    public void deleteAll(){
        noticeRepository.deleteAll();;
    }

    @Test
    public void  getNoticeList() throws Exception{

        Integer noticeId1 = createNotice("notice1");
        Integer noticeId2 = createNotice("notice2");
        Integer noticeId3 = createNotice("notice3");

        System.out.println("hihello"+mvc);

        mvc.perform(get("/notice").
                content(new ObjectMapper().writeValueAsString(1))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void  getNoticeContent() throws Exception{

        Integer noticeId = createNotice("notice");

        mvc.perform(get("/notice/"+noticeId).
                content(new ObjectMapper().writeValueAsString(3))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    public Integer createNotice(String str){
        return noticeRepository.save(
                Notice.builder()
                        .title(str)
                        .description(str)
                        .fileName(str)
                        .createdAt(LocalDateTime.now())
                        .build()
        ).getId();
    }

}