package com.university.skillauditor.skillmanagement.api;

import com.university.skillauditor.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class SkillControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("GET /api/skills without authentication returns 401")
    void test01() throws Exception {
        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/skills with any authenticated role returns 200")
    @WithMockUser(roles = "STAFF")
    void test02() throws Exception {
        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/skills as SKILL_MANAGER creates a skill and returns 201")
    @WithMockUser(roles = "SKILL_MANAGER")
    void test03() throws Exception {
        String requestBody = """
                {
                    "name": "Python Programming",
                    "description": "Core Python skills",
                    "category": "Software Development"
                }
                """;
        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /api/skills as STAFF returns 403 Forbidden")
    @WithMockUser(roles = "STAFF")
    void test04() throws Exception {
        String requestBody = """
                {
                    "name": "Python Programming",
                    "description": "Core Python skills",
                    "category": "Software Development"
                }
                """;

        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/skills/{id} for a nonexistent skill returns 404")
    @WithMockUser(roles = "STAFF")
    void test05() throws Exception {
        mockMvc.perform(get("/api/skills/nonexistent-id"))
                .andExpect(status().isNotFound());
    }
}