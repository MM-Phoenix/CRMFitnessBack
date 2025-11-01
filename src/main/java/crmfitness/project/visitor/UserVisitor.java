package crmfitness.project.visitor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class UserVisitor implements Visitor {

    private final Map<String, String> userInfoBySessions = new ConcurrentHashMap<>();

    @Override
    public String getVisitors() {
        StringBuilder builder = new StringBuilder();

        for (String value : userInfoBySessions.values()) {
            builder.append(value);
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void visit(Admin admin) {
        String fullName = admin.getUser().getFirstName() + " " + admin.getUser().getLastName();
        userInfoBySessions.put(admin.getSession(), "Admin: " + fullName);
    }

    @Override
    public void visit(Client client) {
        String name = client.getUser().getFirstName();
        userInfoBySessions.put(client.getSession(), "Client: " + name);
    }

    @Override
    public void visit(UnregisteredClient unregisteredClient) {
        userInfoBySessions.put(unregisteredClient.getSession(), "Unregistered client");
    }

    @Override
    public void leave(String session) {
        userInfoBySessions.remove(session);
    }
}
