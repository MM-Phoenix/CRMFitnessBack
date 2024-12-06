package crmfitness.project.dto.response;

import crmfitness.project.model.Training;
import crmfitness.project.model.TrainingType;
import lombok.Getter;

@Getter
public class TrainingDto {

    public TrainingDto(Training training) {
        id = training.getId();
        name = training.getName();
        type = training.getType();
    }

    private final Long id;
    private final String name;
    private final TrainingType type;
}
