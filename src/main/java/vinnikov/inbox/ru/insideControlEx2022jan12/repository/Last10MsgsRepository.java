package vinnikov.inbox.ru.insideControlEx2022jan12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.MessageMyUser;

@Repository
public interface Last10MsgsRepository extends JpaRepository<MessageMyUser, Long>
{

}
