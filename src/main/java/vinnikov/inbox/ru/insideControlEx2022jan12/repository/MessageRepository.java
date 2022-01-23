package vinnikov.inbox.ru.insideControlEx2022jan12.repository;

import org.springframework.data.repository.CrudRepository;
import org.yaml.snakeyaml.tokens.Token;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.MessageMyUser;

import java.util.ArrayList;

public interface MessageRepository extends CrudRepository<MessageMyUser, Integer>
{
    MessageMyUser findById(int id);
    ArrayList<MessageMyUser> findAll();
}
