package com.dsmpear.main.domain;

import com.dsmpear.main.entity.notice.Notice;
import com.dsmpear.main.entity.notice.NoticeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

        createNotice("notice1");
        createNotice("notice2");
        createNotice("notice3");

        mvc.perform(get("/notice"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void  getNoticeContent() throws Exception{

        Integer noticeId = createNotice("notice");

        mvc.perform(get("/notice/"+noticeId))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void  getNoticeContent_noId() throws Exception{

        int noticeId = createNotice("notice");

        mvc.perform(get("/notice/"+10000))
                .andExpect(status().isNotFound()).andDo(print());
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
