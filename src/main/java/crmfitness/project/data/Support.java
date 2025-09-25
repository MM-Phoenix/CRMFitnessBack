package crmfitness.project.data;

import org.springframework.stereotype.Component;

@Component
public class Support {

    private SupportState state;

    public String getMessage() {
        return state.getMessage();
    }

    public void setState(SupportState state) {
        this.state = state;
    }
}
