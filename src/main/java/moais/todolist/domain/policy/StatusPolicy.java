package moais.todolist.domain.policy;

import moais.todolist.domain.todo.TodoStatus;

public interface StatusPolicy {

    boolean canStatusModify(TodoStatus asis, TodoStatus tobe);

}
