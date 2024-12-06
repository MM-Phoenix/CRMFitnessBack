package crmfitness.project.service;

import crmfitness.project.model.Subscription;
import crmfitness.project.model.SubscriptionType;
import crmfitness.project.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public List<Subscription> getSubscriptions() {
        return repository.findAll();
    }

    public Subscription getSubscription(SubscriptionType subscriptionType) {
        return repository.findById(subscriptionType)
                .orElseThrow(() -> new RuntimeException("There is no subscription by type: " + subscriptionType));
    }
}
