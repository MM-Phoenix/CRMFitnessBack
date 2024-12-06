package crmfitness.project.dto.response;

import crmfitness.project.model.Schedule;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
public class ScheduleDto {

    public ScheduleDto(Schedule schedule) {
        dateFrom = schedule.getDateFrom();
        dateTo = schedule.getDateTo();
        trainer = new UserDto(schedule.getTrainer(), false);
        training = new TrainingDto(schedule.getTraining());

        if (nonNull(schedule.getRegistrations())) {
            registrations = schedule.getRegistrations().stream()
                    .map(ScheduleRegistrationDto::new)
                    .collect(Collectors.toList());
        }
    }

    private final Long dateFrom;
    private final Long dateTo;
    private final UserDto trainer;
    private final TrainingDto training;
    private List<ScheduleRegistrationDto> registrations;
}
