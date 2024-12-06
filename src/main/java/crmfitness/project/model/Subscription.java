package crmfitness.project.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Subscription {

    @Id
    @Column(length = 30)
    @Enumerated(value = EnumType.STRING)
    private SubscriptionType type;

    @Column
    private int count;
}
