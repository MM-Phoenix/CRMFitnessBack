package crmfitness.project.controller;

import crmfitness.project.dto.request.UserLoginDto;
import crmfitness.project.dto.request.UserRegistrationDto;
import crmfitness.project.dto.response.UserDto;
import crmfitness.project.jwt.data.JWTToken;
import crmfitness.project.jwt.service.JwtService;
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
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserAuthService userAuthService;

    @PostMapping("/signup")
    public UserDto register(@RequestBody UserRegistrationDto userRegistrationDto) {
        User registeredUser = userAuthService.signup(userRegistrationDto, Role.CLIENT);
        return new UserDto(registeredUser);
    }

    @PostMapping("/signing")
    public JWTToken authenticate(@RequestBody UserLoginDto userLoginDto) {
        User user = userAuthService.authenticate(userLoginDto);
        return jwtService.generateToken(user);
    }
}
