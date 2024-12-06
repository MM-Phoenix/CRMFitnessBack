package crmfitness.project.repository;

import crmfitness.project.model.Role;
import crmfitness.project.model.ScheduleUser;
import crmfitness.project.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static crmfitness.project.model.User.SCHEDULES_GRAPH;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = SCHEDULES_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByEmail(String email);

    List<User> getAllByRole(Role role);

    Optional<User> findByIdAndRole(long id, Role role);

    @Query("SELECT u.id AS id, u.firstName AS firstName, u.lastName AS lastName FROM User u LEFT JOIN u.subscription us WHERE us IS NOT NULL")
    List<ScheduleUser> getScheduleUsers();
}
