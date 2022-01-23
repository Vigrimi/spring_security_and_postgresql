package vinnikov.inbox.ru.insideControlEx2022jan12.entity;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Entity
@Table(name = "tb_inside_users")
@Getter
@Setter
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @NotNull(message = "Поле не может быть пустым")
    @Size(min = 3, max = 10, message = "Значения от 3 до 10")
    private String login; // name

    @NotNull(message = "Поле не может быть пустым")
    @Size(min = 6, message = "Значения от 6")
    private String password;

    @ManyToOne
    private Role role;

    @Override
    public String toString() {
        return "MyUser{" +
                "user_id=" + user_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}