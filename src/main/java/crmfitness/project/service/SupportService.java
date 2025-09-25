package crmfitness.project.service;

import crmfitness.project.data.Support;
import crmfitness.project.data.SupportState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SupportService {

    private final Support support;

    public String getMessage() {
        return support.getMessage();
    }

    public void setSupportState(SupportState state) {
        support.setState(state);
    }
}
