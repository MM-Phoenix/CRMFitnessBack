package crmfitness.project.jwt.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTToken {

    private final String bearer;
    private final long expirationDate;
}
