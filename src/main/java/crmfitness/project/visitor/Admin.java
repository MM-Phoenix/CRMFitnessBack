package crmfitness.project.visitor;

import crmfitness.project.model.User;

public class Admin extends Element {

    public Admin(User user, String session) {
        super(user, session);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
