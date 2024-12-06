package crmfitness.project.dto.response;

import crmfitness.project.model.Role;
import crmfitness.project.model.ScheduleUser;
import crmfitness.project.model.SubscriptionType;
import crmfitness.project.model.User;
import lombok.Getter;
import org.hibernate.collection.spi.PersistentBag;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
public class UserDto {

    public UserDto(User user) {
        this(user, true);
    }

    public UserDto(User user, boolean includeSchedule) {
        this.id = user.getId();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        if (includeSchedule && !(user.getSchedules() instanceof PersistentBag)) {
            schedules = user.getSchedules().stream().map(ScheduleDto::new).collect(Collectors.toList());
        }

        if (nonNull(user.getSubscription())) {
            this.subscription = user.getSubscription().getSubscription().getType();
            this.remainingServicesCount = user.getSubscription().getRemainingCount();
        }

        this.scheduleRegistrations = user.getScheduleRegistrations().stream()
                .map(ScheduleRegistrationDto::new).collect(Collectors.toList());
    }

    public UserDto(ScheduleUser scheduleUser) {
        this.id = scheduleUser.getId();
        this.firstName = scheduleUser.getFirstName();
        this.lastName = scheduleUser.getLastName();
    }

    private final long id;
    private Role role;
    private String email;
    private final String firstName;
    private final String lastName;
    private List<ScheduleDto> schedules;
    private SubscriptionType subscription;
    private Integer remainingServicesCount = 0;
    private List<ScheduleRegistrationDto> scheduleRegistrations;
}
