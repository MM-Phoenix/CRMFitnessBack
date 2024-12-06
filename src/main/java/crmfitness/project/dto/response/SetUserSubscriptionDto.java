package crmfitness.project.dto.response;

import crmfitness.project.model.SubscriptionType;
import lombok.Getter;

@Getter
public class SetUserSubscriptionDto {

    private long userId;
    private SubscriptionType subscriptionType;
}
