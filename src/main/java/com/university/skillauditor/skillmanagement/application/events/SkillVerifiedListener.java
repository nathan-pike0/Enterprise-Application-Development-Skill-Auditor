package com.university.skillauditor.skillmanagement.application.events;

import com.university.skillauditor.shared.events.SkillVerifiedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class SkillVerifiedListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(SkillVerifiedEvent event) {
        log.info("Skill {} in portfolio entry {} verified for staff member {} by manager {}",
                event.skillId(), event.portfolioId(), event.staffMemberId(), event.verifiedById());
    }
}