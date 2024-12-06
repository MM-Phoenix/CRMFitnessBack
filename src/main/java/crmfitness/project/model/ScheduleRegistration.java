package crmfitness.project.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule_registration")
@IdClass(ScheduleRegistrationId.class)
public class ScheduleRegistration {

    public ScheduleRegistration() {
    }

    public ScheduleRegistration(Long userId, Long scheduleId) {
        this.userId = userId;
        this.scheduleId = scheduleId;
    }

    @Id
    @Column(name = "user_id")
    private long userId;

    @Id
    @Column(name = "schedule_id")
    private long scheduleId;
}
