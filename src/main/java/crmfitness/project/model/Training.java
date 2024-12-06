package crmfitness.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Training {

    public static final long TRAINING_DURATION = 60 * 60 * 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, length = 30, nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(unique = true, length = 30, nullable = false)
    private TrainingType type;
}
