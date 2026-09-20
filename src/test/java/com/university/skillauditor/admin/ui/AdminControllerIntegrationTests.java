package com.university.skillauditor.admin.ui;

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
public class AdminControllerIntegrationTests {

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
    @DisplayName("POST /api/admin/staff without authentication returns 401")
    void test01() throws Exception {
        mockMvc.perform(post("/api/admin/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/admin/staff as ADMIN returns 202 Accepted")
    @WithMockUser(roles = "ADMIN")
    void test02() throws Exception {
        String requestBody = """
            {
                "firebaseUid": "test-firebase-uid-123",
                "firstName": "Jane",
                "lastName": "Doe",
                "email": "jane.doe@company.com",
                "hireDate": "2025-03-01",
                "departmentId": "dept-456",
                "lineManagerId": "manager-456",
                "currentRole": "QA Engineer",
                "roleStartDate": "2025-03-01",
                "jobLevel": "Mid",
                "employmentType": "FULL_TIME"
            }
            """;
        mockMvc.perform(post("/api/admin/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted());
    }


    @Test
    @DisplayName("POST /api/admin/staff as STAFF returns 403 Forbidden")
    @WithMockUser(roles = "STAFF")
    void test03() throws Exception {
        String requestBody = """
                {
                    "firstName": "Nathan",
                    "lastName": "Pike",
                    "email": "nathan@x.com",
                    "hireDate": "2025-11-11",
                    "departmentId": "departmentttt",
                    "lineManagerId": "manager",
                    "currentRole": "Software Engineer",
                    "roleStartDate": "2025-03-01",
                    "jobLevel": "Mid",
                    "employmentType": "FULL_TIME"
                }
                """;
        mockMvc.perform(post("/api/admin/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("PATCH /api/admin/staff/{id}/details as MANAGER returns 403 Forbidden")
    @WithMockUser(roles = "MANAGER")
    void test04() throws Exception {
        String requestBody = """
                {
                    "firstName": "Johnny",
                    "lastName": "Cash",
                    "email": "johnny@x.com"
                }
                """;
        mockMvc.perform(patch("/api/admin/staff/some-id/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }
}