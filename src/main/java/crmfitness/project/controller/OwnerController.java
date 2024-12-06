package crmfitness.project.controller;

import crmfitness.project.dto.request.UserRegistrationDto;
import crmfitness.project.dto.response.UserDto;
import crmfitness.project.model.Role;
import crmfitness.project.model.User;
import crmfitness.project.service.UserAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

    private final UserAuthService userAuthService;

    @PostMapping("/signup")
    public UserDto register(@RequestBody UserRegistrationDto userRegistrationDto) {
        User registeredUser = userAuthService.signup(userRegistrationDto, Role.ADMIN);
        return new UserDto(registeredUser);
    }
}
