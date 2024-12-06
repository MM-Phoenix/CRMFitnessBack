package crmfitness.project.service;

import crmfitness.project.dto.response.SetUserSubscriptionDto;
import crmfitness.project.model.Subscription;
import crmfitness.project.model.User;
import crmfitness.project.model.UserSubscription;
import crmfitness.project.repository.UserRepository;
import crmfitness.project.repository.UserSubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class UserSubscriptionService {

    private final UserRepository userRepository;
    private final UserSubscriptionRepository repository;
    private final SubscriptionService subscriptionService;

    @Transactional
    public User setUserSubscription(SetUserSubscriptionDto setUserSubscriptionDto) {
        User user = userRepository.findById(setUserSubscriptionDto.getUserId())
                .orElseThrow(() -> new RuntimeException("There is no user by id: " + setUserSubscriptionDto.getUserId()));
        Subscription subscription = subscriptionService.getSubscription(setUserSubscriptionDto.getSubscriptionType());

        if (nonNull(user.getSubscription()) && user.getSubscription().getRemainingCount() != 0) {
            throw new RuntimeException("User subscription is not expired");
        }

        UserSubscription userSubscription = user.getSubscription();

        if (isNull(userSubscription)) {
            userSubscription = new UserSubscription(user, subscription);
            repository.save(userSubscription);
        } else {
            userSubscription.setSubscription(subscription);
            userSubscription.setRemainingCount(subscription.getCount());
        }

        user.setSubscription(userSubscription);
        return user;
    }

    public void decrementUserCount(long userId) {
        repository.decrementUserCount(userId);
    }

    public void incrementUserCount(long userId) {
        repository.incrementUserCount(userId);
    }

    public void incrementUsersCount(List<Long> userIds) {
        repository.incrementUsersCount(userIds);
    }
}
