package crmfitness.project.model.builder;

import crmfitness.project.model.*;

import java.util.List;

public class UserBuilder {

    private final User user;

    private UserBuilder() {
        user = new User();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public User build() {
        return user;
    }

    public UserBuilder setId(long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder setEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(Role role) {
        user.setRole(role);
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder setSchedules(List<Schedule> schedules) {
        user.setSchedules(schedules);
        return this;
    }

    public UserBuilder setSubscription(UserSubscription subscription) {
        user.setSubscription(subscription);
        return this;
    }

    public UserBuilder setScheduleRegistrations(List<ScheduleRegistration> scheduleRegistrations) {
        user.setScheduleRegistrations(scheduleRegistrations);
        return this;
    }
}
