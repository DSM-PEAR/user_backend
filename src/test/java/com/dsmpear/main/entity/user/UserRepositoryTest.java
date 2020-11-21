package com.dsmpear.main.entity.user;

import com.dsmpear.main.MainApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles({"test"})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        this.user = userRepository.save(
                User.builder()
                .email("test@dsm.hs.kr")
                .password("testpasssword")
                .name("tester")
                .authStatus(false)
                .build()
        );
    }

    @AfterEach
    public void exit() {
        userRepository.deleteAll();
    }

    @Test
    public void userRepositorySave() {
        assertEquals(user.getPassword(), userRepository.findByEmail("test@dsm.hs.kr").get().getPassword());
    }
}
