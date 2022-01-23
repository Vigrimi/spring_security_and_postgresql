package vinnikov.inbox.ru.insideControlEx2022jan12.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "tb_inside_roles")
@Getter
@Setter
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
