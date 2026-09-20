package com.university.skillauditor;

import com.university.skillauditor.admin.ui.AdminControllerIntegrationTests;
import com.university.skillauditor.skillmanagement.api.SkillControllerIntegrationTests;
import com.university.skillauditor.skillmanagement.api.SkillPortfolioControllerIntegrationTests;
import com.university.skillauditor.skillmanagement.application.SkillPortfolioServiceTests;
import com.university.skillauditor.skillmanagement.application.SkillServiceTests;
import com.university.skillauditor.skillmanagement.domain.SkillPortfolioTests;
import com.university.skillauditor.skillmanagement.domain.SkillTests;
import com.university.skillauditor.staffmanagement.api.StaffMemberControllerIntegrationTests;
import com.university.skillauditor.staffmanagement.application.StaffMemberServiceTests;
import com.university.skillauditor.staffmanagement.domain.StaffMemberTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        // domain
        SkillTests.class,
        SkillPortfolioTests.class,
        StaffMemberTests.class,

        // service
        SkillServiceTests.class,
        SkillPortfolioServiceTests.class,
        StaffMemberServiceTests.class,

        // integratin
        SkillControllerIntegrationTests.class,
        SkillPortfolioControllerIntegrationTests.class,
        StaffMemberControllerIntegrationTests.class,
        AdminControllerIntegrationTests.class
})
public class TestSuite {
}