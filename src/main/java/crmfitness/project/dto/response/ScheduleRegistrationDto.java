package crmfitness.project.dto.response;

import crmfitness.project.model.ScheduleRegistration;
import lombok.Getter;

@Getter
public class ScheduleRegistrationDto {

    public ScheduleRegistrationDto(ScheduleRegistration scheduleRegistration) {
        this.userId = scheduleRegistration.getUserId();
        this.scheduleId = scheduleRegistration.getScheduleId();
    }

    private final Long userId;
    private final Long scheduleId;
}
