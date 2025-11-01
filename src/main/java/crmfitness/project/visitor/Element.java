package crmfitness.project.visitor;

import crmfitness.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Element {

    private final User user;
    private final String session;

    abstract void accept(Visitor visitor);
}
