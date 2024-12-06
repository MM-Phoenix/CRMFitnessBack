package crmfitness.project.repository;

import crmfitness.project.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.dateFrom <= :dateFrom AND s.dateTo >= :dateFrom")
    Optional<Schedule> findExistsByDate(@Param("dateFrom") long dateFrom);

    @Query("select s FROM Schedule s LEFT JOIN s.trainer AS st WHERE " +
            "s.dateFrom > :currentDate AND st IS NOT NULL")
    List<Schedule> getActiveSchedules(@Param("currentDate") long currentDate);
}
