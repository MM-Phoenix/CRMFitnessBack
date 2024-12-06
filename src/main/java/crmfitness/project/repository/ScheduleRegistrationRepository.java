package crmfitness.project.repository;

import crmfitness.project.model.ScheduleRegistration;
import crmfitness.project.model.ScheduleRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRegistrationRepository extends JpaRepository<ScheduleRegistration, ScheduleRegistrationId> {
}
