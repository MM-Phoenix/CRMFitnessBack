package crmfitness.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
@NamedEntityGraph(name = User.SCHEDULES_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "schedules")}
)
public class User {

    public static final String SCHEDULES_GRAPH = "SCHEDULES_GRAPH";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(unique = true, length = 30, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "trainer")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserSubscription subscription;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<ScheduleRegistration> scheduleRegistrations = new ArrayList<>();
}
