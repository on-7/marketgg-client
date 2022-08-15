package com.nhnacademy.marketgg.client;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("local")
class ClientApplicationTests {

    @Test
    void contextLoads(WebApplicationContext context) {
        assertThat(context).isNotNull();
    }

}
