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
public class SkillPortfolioControllerIntegrationTests {

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
    @DisplayName("POST /api/portfolio without authentication returns 401")
    void test01() throws Exception {
        mockMvc.perform(post("/api/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/portfolio as STAFF creates an entry and returns 201")
    @WithMockUser(roles = "STAFF")
    void test02() throws Exception {
        String requestBody = """
                {
                    "staffMemberId": "staff-123",
                    "skillId": "skill-123",
                    "skillLevel": "BEGINNER",
                    "expiryDate": null
                }
                """;
        mockMvc.perform(post("/api/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /api/portfolio as MANAGER returns 403 Forbidden")
    @WithMockUser(roles = "MANAGER")
    void test03() throws Exception {
        String requestBody = """
                {
                    "staffMemberId": "staff-123",
                    "skillId": "skill-123",
                    "skillLevel": "BEGINNER",
                    "expiryDate": null
                }
                """;
        mockMvc.perform(post("/api/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/portfolio/pending as MANAGER returns 200")
    @WithMockUser(roles = "MANAGER")
    void test04() throws Exception {
        mockMvc.perform(get("/api/portfolio/pending"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("GET /api/portfolio/pending as STAFF returns 403 Forbidden")
    @WithMockUser(roles = "STAFF")
    void test05() throws Exception {
        mockMvc.perform(get("/api/portfolio/pending"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/portfolio/{id} for a nonexistent entry returns 404")
    @WithMockUser(roles = "STAFF")
    void test06() throws Exception {
        mockMvc.perform(get("/api/portfolio/nonexistent-id"))
                .andExpect(status().isNotFound());
    }
}