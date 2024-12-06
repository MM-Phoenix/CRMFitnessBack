package crmfitness.project.service;

import crmfitness.project.model.Training;
import crmfitness.project.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository repository;

    public Optional<Training> findTraining(long trainingId) {
        return repository.findById(trainingId);
    }

    public List<Training> getTrainings() {
        return repository.findAll();
    }
}
