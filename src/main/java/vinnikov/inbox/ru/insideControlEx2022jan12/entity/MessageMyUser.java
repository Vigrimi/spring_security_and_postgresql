package vinnikov.inbox.ru.insideControlEx2022jan12.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_inside_messages")
@Getter
@Setter
public class MessageMyUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_of_msg;

    //@OneToMany(targetEntity = MyUser.class)
    private Integer user_id;

    @Size(max = 1_000)
    private String message_my_user;

    @Override
    public String toString() {
        return "MessageMyUser{" +
                "id_of_msg=" + id_of_msg +
                ", user_id=" + user_id +
                ", message_my_user='" + message_my_user + '\'' +
                '}';
    }
}
