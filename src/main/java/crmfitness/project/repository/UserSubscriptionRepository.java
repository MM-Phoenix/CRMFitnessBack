package crmfitness.project.repository;

import crmfitness.project.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    @Modifying
    @Query("UPDATE UserSubscription us SET us.remainingCount = us.remainingCount - 1 WHERE us.user.id = :userId")
    void decrementUserCount(@Param("userId") long user);

    @Modifying
    @Query("UPDATE UserSubscription us SET us.remainingCount = us.remainingCount + 1 WHERE us.user.id = :userId")
    void incrementUserCount(@Param("userId") long userId);

    @Modifying
    @Query("UPDATE UserSubscription us SET us.remainingCount = us.remainingCount + 1 WHERE us.user.id IN (:userIds)")
    void incrementUsersCount(@Param("userIds") List<Long> userIds);
}
