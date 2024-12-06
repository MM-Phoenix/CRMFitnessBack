package crmfitness.project.dto.request;

import lombok.Getter;

@Getter
public class UserRegistrationDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
