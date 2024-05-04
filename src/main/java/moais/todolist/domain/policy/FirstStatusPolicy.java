package moais.todolist.domain.policy;

import moais.todolist.domain.todo.TodoStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static moais.todolist.domain.todo.TodoStatus.HOLD;
import static moais.todolist.domain.todo.TodoStatus.PROGRESS;

@Component
@Primary
public class FirstStatusPolicy implements StatusPolicy {

    @Override
    public boolean canStatusModify(TodoStatus asis, TodoStatus tobe) {
        if (isEquals(asis, tobe)) {
            return true;
        }

        if (HOLD.equals(tobe)) {
            return PROGRESS.equals(asis);
        }

        return true;
    }

    private boolean isEquals(TodoStatus asis, TodoStatus tobe) {
        return asis.equals(tobe);
    }

}
