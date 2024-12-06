package crmfitness.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRegistrationId implements Serializable {

    private Long userId;
    private Long scheduleId;
}
