package daos.message;

import entities.message.MessageEntity;

import java.util.List;

public interface MessageDAO {

    void save(MessageEntity message);
    List<MessageEntity> get(String username);
}
