package persistency.shared.daos;

import persistency.shared.entities.MessageEntity;

import java.util.List;

public interface MessageDAO {

    void save(MessageEntity message);
    List<MessageEntity> get(String username);
}
