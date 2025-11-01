package crmfitness.project.visitor;

public interface Visitor {

    String getVisitors();

    void visit(Admin admin);

    void visit(Client client);

    void visit(UnregisteredClient unregisteredClient);

    void leave(String session);
}
