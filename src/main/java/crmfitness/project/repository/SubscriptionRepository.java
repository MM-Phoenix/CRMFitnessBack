package crmfitness.project.repository;

import crmfitness.project.model.Subscription;
import crmfitness.project.model.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionType> {
}
