package com.university.skillauditor.staffmanagement.application.events;

import com.university.skillauditor.shared.events.NewStaffMemberAddedEvent;
import com.university.skillauditor.staffmanagement.application.StaffMemberService;
import com.university.skillauditor.staffmanagement.domain.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@RabbitListener(queues = "newStaffMemberAdded")
public class NewStaffMemberAddedListener {

    private final StaffMemberService staffMemberService;

    @RabbitHandler
    public void receiver(NewStaffMemberAddedEvent event) {
        try {
            log.info("Received NewStaffMemberAddedEvent for staff {}", event.staffMemberId());
            staffMemberService.createStaffMember(
                    event.staffMemberId(),
                    event.firstName(),
                    event.lastName(),
                    event.email(),
                    event.hireDate(),
                    event.departmentId(),
                    event.lineManagerId(),
                    event.currentRole(),
                    event.roleStartDate(),
                    event.jobLevel(),
                    EmploymentType.valueOf(event.employmentType())
            );
        } catch (Exception e) {
            log.error("Failed to process NewStaffMemberAddedEvent for staff {}: {}",
                    event.staffMemberId(), e.getMessage());
        }
    }
}