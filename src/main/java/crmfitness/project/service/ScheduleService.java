package crmfitness.project.service;

import crmfitness.project.dto.request.ScheduleCreateDto;
import crmfitness.project.dto.request.ScheduleUpdateDto;
import crmfitness.project.model.*;
import crmfitness.project.model.builder.ScheduleBuilder;
import crmfitness.project.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static crmfitness.project.model.Training.TRAINING_DURATION;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final UserService userService;
    private final ScheduleRepository repository;
    private final TrainingService trainingService;
    private final UserSubscriptionService userSubscriptionService;

    public List<Schedule> getSchedules() {
        return repository.findAll();
    }

    @Transactional
    public Schedule createSchedule(ScheduleCreateDto scheduleCreateDto) {
        checkScheduleDateFrom(scheduleCreateDto.getDateFrom());
        checkScheduleExisting(scheduleCreateDto);

        User trainer = userService.findTrainer(scheduleCreateDto.getTrainerId())
                .orElseThrow(() -> new RuntimeException("There is no trainer by id"));
        Training training = trainingService.findTraining(scheduleCreateDto.getTrainingId())
                .orElseThrow(() -> new RuntimeException("There is no training by id"));

        Schedule schedule = ScheduleBuilder.builder()
                .setDateFrom(scheduleCreateDto.getDateFrom())
                .setDateTo(scheduleCreateDto.getDateFrom() + TRAINING_DURATION)
                .setTrainer(trainer)
                .setTraining(training)
                .build();

        return repository.save(schedule);
    }

    private void checkScheduleDateFrom(long dateFrom) {
        if (dateFrom < getScheduleFirstWorkedMillis()) {
            throw new RuntimeException("Wrong date from");
        }
    }

    private void checkScheduleExisting(ScheduleCreateDto scheduleCreateDto) {
        repository.findExistsByDate(scheduleCreateDto.getDateFrom())
                .ifPresent((schedule -> {
                    if (schedule.getDateTo() > scheduleCreateDto.getDateFrom()) {
                        throw new RuntimeException("Training already exists at the same time");
                    }
                }));
    }

    @Transactional
    public Schedule updateSchedule(ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = repository.findExistsByDate(scheduleUpdateDto.getDateFrom())
                .orElseThrow(() -> new RuntimeException("There is no schedule"));
        updateTrainer(schedule, scheduleUpdateDto.getTrainerId());
        updateTraining(schedule, scheduleUpdateDto.getTrainingId());

        return schedule;
    }

    private void updateTrainer(Schedule schedule, long trainerId) {
        if (schedule.getTrainer().getId() != trainerId) {
            User trainer = userService.findTrainer(trainerId).orElseThrow(() -> new RuntimeException("There is no trainer"));
            schedule.setTrainer(trainer);
        }
    }

    private void updateTraining(Schedule schedule, long trainingId) {
        if (schedule.getTraining().getId() != trainingId) {
            Training training = trainingService.findTraining(trainingId)
                    .orElseThrow(() -> new RuntimeException("There is no training by id"));
            schedule.setTraining(training);
        }
    }

    @Transactional
    public void deleteSchedule(long dateFrom) {
        Schedule schedule = repository.findExistsByDate(dateFrom)
                .orElseThrow(() -> new RuntimeException("There is no schedule"));

        List<Long> userIds = schedule.getRegistrations().stream().map(ScheduleRegistration::getUserId).collect(Collectors.toList());
        userSubscriptionService.incrementUsersCount(userIds);

        repository.delete(schedule);
    }

    public Schedule getScheduleForRegistration(long scheduleId, User user) {
        Schedule schedule = repository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("There is no schedule on this time"));

        checkIfActive(schedule);
        checkIfClientTryToRegister(user);
        checkIfAlreadyRegistered(schedule, user);

        return schedule;
    }

    public Schedule getClientActiveSchedule(long scheduleId, User user) {
        Schedule schedule = repository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("There is no schedule on this time"));

        checkIfActive(schedule);
        checkIfClientTryToRegister(user);

        return schedule;
    }

    private void checkIfActive(Schedule schedule) {
        if (schedule.getDateFrom() < System.currentTimeMillis()) {
            throw new RuntimeException("Schedule is not active");
        }
    }

    private void checkIfClientTryToRegister(User user) {
        if (!Role.CLIENT.equals(user.getRole())) {
            throw new RuntimeException("Only client can register on schedule");
        }
    }

    private void checkIfAlreadyRegistered(Schedule schedule, User user) {
        schedule.getRegistrations().stream()
                .filter(scheduleRegistration -> scheduleRegistration.getUserId() == user.getId())
                .findFirst()
                .ifPresent(scheduleRegistration -> {
                    throw new RuntimeException("You already registered");
                });
    }

    public long getScheduleFirstWorkedMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }
}
