package crmfitness.project.service;

import crmfitness.project.data.SocketKey;
import crmfitness.project.data.Support;
import crmfitness.project.data.SupportStateType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ClientAdminChatService {

    private final Support support;
    private final Random random = new Random();

    private final Map<String, SocketKey> adminSocketKeyBySessions = new ConcurrentHashMap<>();
    private final Map<String, SocketKey> clientSocketKeyBySessions = new ConcurrentHashMap<>();

    private final Map<String, SocketKey> clientSocketByAdminSessions = new ConcurrentHashMap<>();
    private final Map<String, SocketKey> adminSocketKeyByClientSessions = new ConcurrentHashMap<>();

    public void addAdmin(String adminSession, SocketKey socketKey) {
        adminSocketKeyBySessions.put(adminSession, socketKey);
    }

    public void removeAdmin(String adminSession) {
        adminSocketKeyBySessions.remove(adminSession);
        clientSocketByAdminSessions.remove(adminSession);
        adminSocketKeyByClientSessions.values().removeIf(value -> value.getSession().equals(adminSession));
    }

    public void addClient(String clientSession, SocketKey socketKey) {
        clientSocketKeyBySessions.put(clientSession, socketKey);
    }

    public void removeClient(String clientSession) {
        clientSocketKeyBySessions.remove(clientSession);
        adminSocketKeyByClientSessions.remove(clientSession);
        clientSocketByAdminSessions.values().removeIf(value -> value.getSession().equals(clientSession));
    }

    public void assignAdminToUser(String clientSession) {
        if (!adminSocketKeyBySessions.isEmpty()) {
            if (SupportStateType.WORKING.equals(support.getType())) {
                List<SocketKey> adminList = new ArrayList<>(adminSocketKeyBySessions.values());

                SocketKey randomAdminSocketKey = adminList.get(random.nextInt(adminList.size()));
                adminSocketKeyByClientSessions.put(clientSession, randomAdminSocketKey);

                SocketKey clientSocketKey = clientSocketKeyBySessions.get(clientSession);
                clientSocketByAdminSessions.put(randomAdminSocketKey.getSession(), clientSocketKey);
            }
        }
    }

    public Optional<SocketKey> getAdmin(String adminSession) {
        return Optional.ofNullable(adminSocketKeyBySessions.get(adminSession));
    }

    public Optional<SocketKey> getClient(String clientSession) {
        return Optional.ofNullable(clientSocketKeyBySessions.get(clientSession));
    }

    public Optional<SocketKey> getAdminForUser(String clientSession) {
        return Optional.ofNullable(adminSocketKeyByClientSessions.get(clientSession));
    }

    public Optional<SocketKey> getUserForAdmin(String adminSession) {
        return Optional.ofNullable(clientSocketByAdminSessions.get(adminSession));
    }
}
