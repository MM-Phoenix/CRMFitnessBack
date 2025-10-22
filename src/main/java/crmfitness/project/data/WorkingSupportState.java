package crmfitness.project.data;

import org.springframework.stereotype.Component;

@Component
public class WorkingSupportState implements SupportState {

    @Override
    public String getMessage() {
        return "Admin is connecting...";
    }

    @Override
    public SupportStateType getType() {
        return SupportStateType.WORKING;
    }
}
