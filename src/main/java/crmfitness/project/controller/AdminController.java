package crmfitness.project.controller;

import crmfitness.project.dto.request.ScheduleCreateDto;
import crmfitness.project.dto.request.ScheduleUpdateDto;
import crmfitness.project.dto.request.UserRegistrationDto;
import crmfitness.project.dto.response.ScheduleDto;
import crmfitness.project.dto.response.SetUserSubscriptionDto;
import crmfitness.project.dto.response.TrainingDto;
import crmfitness.project.dto.response.UserDto;
import crmfitness.project.model.*;
import crmfitness.project.service.*;
import crmfitness.project.visitor.Visitor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final Visitor visitor;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final UserAuthService userAuthService;
    private final TrainingService trainingService;
    private final SubscriptionService subscriptionService;
    private final UserSubscriptionService userSubscriptionService;

    @PostMapping("/client/signup")
    public UserDto clientRegister(@RequestBody UserRegistrationDto userRegistrationDto) {
        User registeredUser = userAuthService.signup(userRegistrationDto, Role.CLIENT);
        return new UserDto(registeredUser);
    }

    @PostMapping("/trainer/signup")
    public UserDto trainerRegister(@RequestBody UserRegistrationDto userRegistrationDto) {
        User registeredUser = userAuthService.signup(userRegistrationDto, Role.TRAINER);
        return new UserDto(registeredUser);
    }

    @GetMapping("/subscriptions")
    public List<Subscription> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }

    @GetMapping("/users/clients")
    public List<UserDto> getUsers() {
        List<User> users = userService.getAllClients();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @PostMapping("/user/subscription")
    public UserDto setUserSubscription(@RequestBody SetUserSubscriptionDto setUserSubscriptionDto) {
        User user = userSubscriptionService.setUserSubscription(setUserSubscriptionDto);
        return new UserDto(user);
    }

    @GetMapping("/users/trainers")
    public List<UserDto> getTrainers() {
        return userService.getAllTrainers().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/trainings")
    public List<TrainingDto> getTrainings() {
        return trainingService.getTrainings().stream().map(TrainingDto::new).collect(Collectors.toList());
    }

    @PostMapping("/schedule")
    public ScheduleDto createSchedule(@RequestBody ScheduleCreateDto scheduleCreateDto) {
        Schedule schedule = scheduleService.createSchedule(scheduleCreateDto);
        return new ScheduleDto(schedule);
    }

    @PutMapping("/schedule")
    public ScheduleDto updateSchedule(@RequestBody ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = scheduleService.updateSchedule(scheduleUpdateDto);
        return new ScheduleDto(schedule);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public void deleteSchedule(@PathVariable long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    @GetMapping("/schedule/users")
    public Map<Long, UserDto> getScheduleUsers() {
        List<ScheduleUser> users = userService.getScheduleUsers();
        return users.stream().collect(Collectors.toMap(ScheduleUser::getId, UserDto::new));
    }

    @GetMapping("/visitors")
    public String getVisitors() {
        return visitor.getVisitors();
    }
}
