package moais.todolist.domain.todo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moais.todolist.domain.BaseEntity;
import moais.todolist.domain.account.Account;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public void update(String content, TodoStatus status) {
        this.content = content;
        this.status = status;
    }

}
