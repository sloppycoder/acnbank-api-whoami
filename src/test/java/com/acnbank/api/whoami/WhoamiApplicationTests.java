package com.acnbank.api.whoami;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Scanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WhoamiApplication.class)
@AutoConfigureMockMvc
public class WhoamiApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void actuator_works() throws Exception {
        mockMvc.perform(get("/actuator/info"))
            .andExpect(status().isOk());
    }

    @Test
    public void api_whoami_not_working_without_jwt() throws Exception {
        mockMvc.perform(get("/api/whoami"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void api_whoami_works_with_jwt() throws Exception {
        var result = mockMvc.perform(
                    get("/api/whoami")
                        .header("Authorization", "Bearer " + getTestJWT()))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    private String getTestJWT() throws IOException {
        ClassPathResource resource = new ClassPathResource("test_jwt.txt");
        Scanner scanner = new Scanner(resource.getInputStream());
        String content = scanner.useDelimiter("\\A").next();
        scanner.close();

        return content;
    }
}
