package crmfitness.project.model.builder;

import crmfitness.project.model.Schedule;
import crmfitness.project.model.Training;
import crmfitness.project.model.User;

public class ScheduleBuilder {

    private Long dateFrom;
    private Long dateTo;
    private User trainer;
    private Training training;

    private ScheduleBuilder() {
    }

    public static ScheduleBuilder builder() {
        return new ScheduleBuilder();
    }

    public Schedule build() {
        return new Schedule(dateFrom, dateTo, trainer, training);
    }

    public ScheduleBuilder setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public ScheduleBuilder setDateTo(Long dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public ScheduleBuilder setTrainer(User trainer) {
        this.trainer = trainer;
        return this;
    }

    public ScheduleBuilder setTraining(Training training) {
        this.training = training;
        return this;
    }
}
