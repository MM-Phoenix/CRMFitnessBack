package crmfitness.project.controller;

import crmfitness.project.dto.request.UserUpdateDto;
import crmfitness.project.dto.response.ScheduleDto;
import crmfitness.project.dto.response.UserDto;
import crmfitness.project.jwt.data.UserAuth;
import crmfitness.project.model.Schedule;
import crmfitness.project.model.User;
import crmfitness.project.service.ScheduleRegistrationService;
import crmfitness.project.service.ScheduleService;
import crmfitness.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ScheduleService scheduleService;
    private final ScheduleRegistrationService scheduleRegistrationService;

    @GetMapping("/")
    public UserDto getUser(UserAuth userAuth) {
        User currentUser = userAuth.getUser();
        return new UserDto(currentUser);
    }

    @PutMapping("/")
    public UserDto updateUser(@RequestBody UserUpdateDto userUpdateDto, UserAuth userAuth) {
        User updatedUser = userService.updateUser(userUpdateDto, userAuth.getUser());
        return new UserDto(updatedUser);
    }

    @GetMapping("/schedule/first-worked-millis")
    public long getScheduleFirstWorkedMillis() {
        return scheduleService.getScheduleFirstWorkedMillis();
    }

    @GetMapping("/schedules")
    public List<ScheduleDto> getSchedules() {
        return scheduleService.getSchedules().stream().map(ScheduleDto::new).collect(Collectors.toList());
    }

    @PostMapping("/schedule/{scheduleId}")
    public ScheduleDto registerOnSchedule(@PathVariable long scheduleId, UserAuth userAuth) {
        Schedule schedule = scheduleRegistrationService.registerOnSchedule(scheduleId, userAuth.getUser());
        return new ScheduleDto(schedule);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public ScheduleDto unregisterOnSchedule(@PathVariable long scheduleId, UserAuth userAuth) {
        Schedule schedule = scheduleRegistrationService.unregisterOnSchedule(scheduleId, userAuth.getUser());
        return new ScheduleDto(schedule);
    }
}
