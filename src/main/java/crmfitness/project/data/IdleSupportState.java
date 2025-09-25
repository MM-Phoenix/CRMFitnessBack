package crmfitness.project.data;

import org.springframework.stereotype.Component;

@Component
public class IdleSupportState implements SupportState {

    @Override
    public String getMessage() {
        return "We are closed now. Try again from 10:00 to 18:00.";
    }
}
