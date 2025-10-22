package crmfitness.project.service;

import crmfitness.project.data.Support;
import crmfitness.project.data.SupportStateType;
import crmfitness.project.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ClientAdminChatService {

    private final Support support;
    private final Random random = new Random();
    private final Map<String, User> adminsBySessions = new ConcurrentHashMap<>();
    private final Map<String, String> adminSessionsByClientSessions = new ConcurrentHashMap<>();

    public void addAdmin(String adminSession, User admin) {
        adminsBySessions.put(adminSession, admin);
    }

    public void removeAdmin(String adminSession) {
        adminsBySessions.remove(adminSession);
        adminSessionsByClientSessions.values().removeIf(value -> value.equals(adminSession));
    }

    public void addClient(String clientSession) {
        adminSessionsByClientSessions.put(clientSession, null);
    }

    public void removeClient(String clientSession) {
        adminSessionsByClientSessions.remove(clientSession);
    }

    public String assignAdminToUser(String clientSession) {
        if (adminsBySessions.isEmpty()) return "No admin available.";

        if (SupportStateType.IDLE.equals(support.getType())) {
            List<String> adminList = new ArrayList<>(adminsBySessions.keySet());
            String randomAdminSession = adminList.get(random.nextInt(adminList.size()));
            adminSessionsByClientSessions.put(clientSession, randomAdminSession);
        }

        return support.getMessage();
    }

    public Optional<String> getAdminForUser(String clientSession) {
        return Optional.ofNullable(adminSessionsByClientSessions.get(clientSession));
    }

    public Optional<String> getUserForAdmin(String adminSession) {
        return adminSessionsByClientSessions.entrySet().stream()
                .filter(e -> e.getValue().equals(adminSession))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
