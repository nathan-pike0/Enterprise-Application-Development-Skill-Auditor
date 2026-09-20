package com.university.skillauditor.staffmanagement.application.events;

import com.university.skillauditor.shared.events.StaffDetailsUpdatedEvent;
import com.university.skillauditor.staffmanagement.application.StaffMemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@RabbitListener(queues = "staffDetailsUpdated")
public class StaffDetailsUpdatedListener {

    private final StaffMemberService staffMemberService;

    @RabbitHandler
    public void receiver(StaffDetailsUpdatedEvent event) {
        try {
            log.info("Received StaffDetailsUpdatedEvent for staff {}", event.staffMemberId());
            staffMemberService.updatePersonalDetails(
                    event.staffMemberId(),
                    event.firstName(),
                    event.lastName(),
                    event.email()
            );


        } catch (Exception e) {
            log.error("Failed to process StaffDetailsUpdatedEvent for staff {}: {}",
                    event.staffMemberId(), e.getMessage());
        }
    }
}