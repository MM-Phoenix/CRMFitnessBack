package crmfitness.project.jwt.data;

import crmfitness.project.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public class UserAuth extends UsernamePasswordAuthenticationToken {

    public UserAuth(User user) {
        super(user, null, Set.of(new SimpleGrantedAuthority(user.getRole().name())));
    }

    public User getUser() {
        return (User) getPrincipal();
    }
}
