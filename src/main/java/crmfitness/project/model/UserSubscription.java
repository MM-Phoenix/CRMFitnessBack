package crmfitness.project.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_subscription")
public class UserSubscription {

    public UserSubscription() {
    }

    public UserSubscription(User user, Subscription subscription) {
        this.user = user;
        this.subscription = subscription;
        this.remainingCount = subscription.getCount();
    }

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "subscription")
    private User user;

    @OneToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column
    private int remainingCount;

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }

    public void decrementRemainingCount() {
        this.remainingCount--;
    }
}
