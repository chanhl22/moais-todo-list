package moais.todolist.domain.policy;

import moais.todolist.domain.todo.TodoStatus;
import org.springframework.stereotype.Component;

import static moais.todolist.domain.todo.TodoStatus.HOLD;
import static moais.todolist.domain.todo.TodoStatus.PROGRESS;

@Component
public class SecondStatusPolicy implements StatusPolicy {

    @Override
    public boolean canStatusModify(TodoStatus asis, TodoStatus tobe) {
        //추후에 새로운 조건으로 바뀜
        return true;
    }

}
