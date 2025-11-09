package persistency.persistent.daos;

import persistency.shared.daos.MessageDAO;
import persistency.shared.entities.MessageEntity;

import java.util.List;

public class PMessageDAO implements MessageDAO {
    @Override
    public void save(MessageEntity message) {
        //TODO
    }

    @Override
    public List<MessageEntity> get(String username) {
        //TODO
        return List.of();
    }
}
