package com.university.skillauditor.staffmanagement.application.events;

import com.university.skillauditor.shared.events.StaffDepartmentUpdatedEvent;
import com.university.skillauditor.staffmanagement.application.StaffMemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@RabbitListener(queues = "staffDepartmentUpdated")
public class StaffDepartmentUpdatedListener {
    private final StaffMemberService staffMemberService;

    @RabbitHandler
    public void receiver(StaffDepartmentUpdatedEvent event) {

        try {
            log.info("Received StaffDepartmentUpdatedEvent for staff {}", event.staffMemberId());
            staffMemberService.updateDepartment(
                    event.staffMemberId(),
                    event.departmentId()
            );


        } catch (Exception e) {
            log.error("Failed to process StaffDepartmentUpdatedEvent for staff {}: {}",
                    event.staffMemberId(), e.getMessage());
        }
    }
}