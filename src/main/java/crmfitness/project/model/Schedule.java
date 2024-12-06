package crmfitness.project.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Schedule {

    public Schedule() {
    }

    public Schedule(Long dateFrom, Long dateTo, User trainer, Training training) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.trainer = trainer;
        this.training = training;
    }

    @Id
    @Column(name = "date_from", nullable = false)
    private Long dateFrom;

    @Column(name = "date_to", nullable = false)
    private Long dateTo;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @OneToMany(mappedBy = "scheduleId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ScheduleRegistration> registrations = new ArrayList<>();

    public void addScheduleRegistration(ScheduleRegistration scheduleRegistration) {
        registrations.add(scheduleRegistration);
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
