package daos.message;

import entities.message.MessageEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPMessageDAO implements MessageDAO {

    private final Map<String, List<MessageEntity>> messages = new HashMap<>();

    @Override
    public void save(MessageEntity message) {
        messages.computeIfAbsent(message.getUsername(), _ -> new ArrayList<>()).add(message);
    }

    @Override
    public List<MessageEntity> get(String username) {
        return messages.get(username);
    }
}
