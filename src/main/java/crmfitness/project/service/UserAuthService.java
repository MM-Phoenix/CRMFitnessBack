package crmfitness.project.service;

import crmfitness.project.dto.request.UserLoginDto;
import crmfitness.project.dto.request.UserRegistrationDto;
import crmfitness.project.model.Role;
import crmfitness.project.model.User;
import crmfitness.project.model.builder.UserBuilder;
import crmfitness.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User signup(UserRegistrationDto userRegistration, Role role) {
        try {
            User user = UserBuilder.builder()
                    .setRole(role)
                    .setEmail(userRegistration.getEmail())
                    .setFirstName(userRegistration.getFirstName())
                    .setLastName(userRegistration.getLastName())
                    .setPassword(passwordEncoder.encode(userRegistration.getPassword()))
                    .build();

            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("This email already exists");
        }
    }

    public User authenticate(UserLoginDto userLogin) {
        Optional<User> userOpt = repository.findByEmail(userLogin.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new RuntimeException("Wrong login or password");
    }
}
