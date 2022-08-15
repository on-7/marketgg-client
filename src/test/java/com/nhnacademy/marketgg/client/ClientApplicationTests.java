package com.nhnacademy.marketgg.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({ "local" })
@SpringBootTest
@ActiveProfiles({ "common", "local" })
class ClientApplicationTests {

    @Test
    void contextLoads(WebApplicationContext context) {
        assertThat(context).isNotNull();
    }

}
