package vinnikov.inbox.ru.insideControlEx2022jan12.repository;

import org.springframework.data.repository.CrudRepository;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.MyUser;

public interface UserRepository extends CrudRepository<MyUser, Integer> {
    MyUser findByLogin(String login);
}