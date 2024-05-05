package moais.todolist.domain.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moais.todolist.domain.BaseEntity;
import moais.todolist.domain.todo.Todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "constraintName", columnNames = {"username"}))
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String role;

    @OneToMany(mappedBy = "account")
    private List<Todo> todos = new ArrayList<>();

}

