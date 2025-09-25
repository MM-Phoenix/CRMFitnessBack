package crmfitness.project.schedule;

import crmfitness.project.data.IdleSupportState;
import crmfitness.project.data.WorkingSupportState;
import crmfitness.project.service.SupportService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class SupportSchedule {

    private final SupportService supportService;
    private final IdleSupportState idleSupportState;
    private final WorkingSupportState workingSupportState;

    @PostConstruct
    public void init() {
        LocalTime now = LocalTime.now();

        if (now.getHour() >= 9 && now.getHour() < 18) {
            supportService.setSupportState(workingSupportState);
        } else {
            supportService.setSupportState(idleSupportState);
        }
    }

    @Scheduled(cron = "${support.working-hours-starts}")
    public void workingHoursStarts() {
        supportService.setSupportState(workingSupportState);
    }

    @Scheduled(cron = "${support.idle-hours-starts}")
    public void idleHoursStarts() {
        supportService.setSupportState(idleSupportState);
    }
}
