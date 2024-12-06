package crmfitness.project.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateDto {

    private String email;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
}
