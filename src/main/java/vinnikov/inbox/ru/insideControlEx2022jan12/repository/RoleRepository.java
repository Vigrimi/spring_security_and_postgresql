package vinnikov.inbox.ru.insideControlEx2022jan12.repository;

import org.springframework.data.repository.CrudRepository;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}