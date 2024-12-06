package crmfitness.project.dto.request;

import lombok.Getter;

@Getter
public class ScheduleCreateDto {

    private long dateFrom;
    private long trainerId;
    private long trainingId;
}
