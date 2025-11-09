package persistency.nonpersistent.daos;

import persistency.shared.daos.MessageDAO;
import persistency.shared.entities.MessageEntity;

import java.util.*;

public class NPMessageDAO implements MessageDAO {

    private static NPMessageDAO instance;
    public static NPMessageDAO getInstance() {

        if (instance == null) {
            instance = new NPMessageDAO();
        }

        return instance;
    }
    private NPMessageDAO() {}

    private final Map<String, List<MessageEntity>> messages = new HashMap<>();

    @Override
    public void save(MessageEntity message) {
        messages.computeIfAbsent(message.getUsername(), k -> new ArrayList<>()).add(message);
    }

    @Override
    public List<MessageEntity> get(String username) {
        return messages.get(username);
    }
}
