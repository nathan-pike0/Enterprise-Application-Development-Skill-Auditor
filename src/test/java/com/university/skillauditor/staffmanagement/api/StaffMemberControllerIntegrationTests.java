package com.university.skillauditor.staffmanagement.api;

import com.university.skillauditor.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
public class StaffMemberControllerIntegrationTests {

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
    @DisplayName("GET /api/staff without authentication returns 401")
    void test01() throws Exception {
        mockMvc.perform(get("/api/staff"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/staff as MANAGER returns 200")
    @WithMockUser(roles = "MANAGER")
    void test02() throws Exception {
        mockMvc.perform(get("/api/staff"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/staff as STAFF returns 403 Forbidden")
    @WithMockUser(roles = "STAFF")
    void test03() throws Exception {
        mockMvc.perform(get("/api/staff"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/staff/{id} for a nonexistent staff member returns 404")
    @WithMockUser(roles = "MANAGER")
    void test04() throws Exception {
        mockMvc.perform(get("/api/staff/nonexistent-id"))
                .andExpect(status().isNotFound());
    }
}