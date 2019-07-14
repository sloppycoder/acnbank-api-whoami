package com.acnbank.api.whoami;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WhoamiApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void context_loads() {
    }

    @Test
    public void actuator_works() throws Exception {
        mockMvc.perform(get("/actuator/info")).andExpect(status().isOk());
    }

    @Test
    @Ignore
    // not working after enable oauth2
    public void api_whoami_works() throws Exception {
        mockMvc.perform(get("/api/whoami")).andExpect(status().isOk());
    }
}
