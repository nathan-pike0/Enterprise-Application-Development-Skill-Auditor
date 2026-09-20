package com.university.skillauditor.staffmanagement.application.events;

import com.university.skillauditor.shared.events.StaffPlacementUpdatedEvent;
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
@RabbitListener(queues = "staffPlacementUpdated")
public class StaffPlacementUpdatedListener {

    private final StaffMemberService staffMemberService;

    @RabbitHandler
    public void receiver(StaffPlacementUpdatedEvent event) {
        try {
            log.info("Received StaffPlacementUpdatedEvent for staff {}", event.staffMemberId());
            staffMemberService.updatePlacement(
                    event.staffMemberId(),
                    event.currentRole(),
                    event.jobLevel(),
                    EmploymentType.valueOf(event.employmentType())
            );
        } catch (Exception e) {
            log.error("Failed to process StaffPlacementUpdatedEvent for staff {}: {}",
                    event.staffMemberId(), e.getMessage());
        }
    }
}