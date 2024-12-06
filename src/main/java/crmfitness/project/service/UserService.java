package crmfitness.project.service;

import crmfitness.project.dto.request.UserUpdateDto;
import crmfitness.project.model.Role;
import crmfitness.project.model.ScheduleUser;
import crmfitness.project.model.User;
import crmfitness.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User getById(long userId) {
        return repository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public User updateUser(UserUpdateDto userUpdateDto, User user) {
        user = repository.save(user);
        user.setEmail(userUpdateDto.getEmail());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());

        if (userUpdateDto.getNewPassword() != null) {
            if (!passwordEncoder.matches(userUpdateDto.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Old password is not correct");
            }
            user.setPassword(passwordEncoder.encode(userUpdateDto.getNewPassword()));
        }
        return user;
    }

    public List<ScheduleUser> getScheduleUsers() {
        return repository.getScheduleUsers();
    }

    public List<User> getAllClients() {
        return repository.getAllByRole(Role.CLIENT);
    }

    @Transactional
    public List<User> getAllTrainers() {
        return repository.getAllByRole(Role.TRAINER);
    }

    public Optional<User> findTrainer(long userId) {
        return repository.findByIdAndRole(userId, Role.TRAINER);
    }
}
