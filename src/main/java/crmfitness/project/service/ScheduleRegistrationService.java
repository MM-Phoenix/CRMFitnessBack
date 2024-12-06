package crmfitness.project.service;

import crmfitness.project.model.Schedule;
import crmfitness.project.model.ScheduleRegistration;
import crmfitness.project.model.User;
import crmfitness.project.repository.ScheduleRegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ScheduleRegistrationService {

    private final ScheduleService scheduleService;
    private final ScheduleRegistrationRepository repository;
    private final UserSubscriptionService userSubscriptionService;

    @Transactional
    public Schedule registerOnSchedule(long scheduleId, User user) {
        Schedule schedule = scheduleService.getScheduleForRegistration(scheduleId, user);

        if (isNull(user.getSubscription()) || user.getSubscription().getRemainingCount() == 0) {
            throw new RuntimeException("There is no subscription remaining count");
        }
        userSubscriptionService.decrementUserCount(user.getId());

        ScheduleRegistration scheduleRegistration = repository.saveAndFlush(new ScheduleRegistration(user.getId(), schedule.getDateFrom()));
        schedule.addScheduleRegistration(scheduleRegistration);

        user.getSubscription().decrementRemainingCount();

        return schedule;
    }

    @Transactional
    public Schedule unregisterOnSchedule(long scheduleId, User user) {
        Schedule schedule = scheduleService.getClientActiveSchedule(scheduleId, user);

        ScheduleRegistration scheduleRegistration = schedule.getRegistrations().stream()
                .filter(registration -> registration.getUserId() == user.getId())
                .findFirst().orElseThrow(() -> new RuntimeException("There is no client registration"));

        userSubscriptionService.incrementUserCount(user.getId());
        schedule.getRegistrations().remove(scheduleRegistration);

        return schedule;
    }
}
