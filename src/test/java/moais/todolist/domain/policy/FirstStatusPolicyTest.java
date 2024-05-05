package moais.todolist.domain.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static moais.todolist.domain.todo.TodoStatus.DONE;
import static moais.todolist.domain.todo.TodoStatus.PENDING;
import static moais.todolist.domain.todo.TodoStatus.IN_PROGRESS;
import static moais.todolist.domain.todo.TodoStatus.TODO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FirstStatusPolicyTest {

    @Autowired
    private FirstStatusPolicy firstStatusPolicy;

    @DisplayName("진행 중 상태와 대기 상태만 대기 상태로 변경될 수 있다.")
    @Test
    void progressAndHoldCanModifyToHole() {
        //given //when
        boolean result1 = firstStatusPolicy.canStatusModify(TODO, PENDING);
        boolean result2 = firstStatusPolicy.canStatusModify(IN_PROGRESS, PENDING);
        boolean result3 = firstStatusPolicy.canStatusModify(DONE, PENDING);
        boolean result4 = firstStatusPolicy.canStatusModify(PENDING, PENDING);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isFalse();
        assertThat(result4).isTrue();
    }

    @DisplayName("진행 중 상태에서는 어떤 상태로든 변경할 수 있다.")
    @Test
    void ifStatusProgress() {
        //given //when
        boolean result1 = firstStatusPolicy.canStatusModify(IN_PROGRESS, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(IN_PROGRESS, IN_PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(IN_PROGRESS, DONE);
        boolean result4 = firstStatusPolicy.canStatusModify(IN_PROGRESS, PENDING);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();
    }

    @DisplayName("할 일 상태에서는 대기 상태를 제외하고 변경할 수 있다.")
    @Test
    void ifStatusTodo() {
        //given //when
        boolean result1 = firstStatusPolicy.canStatusModify(TODO, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(TODO, IN_PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(TODO, DONE);
        boolean result4 = firstStatusPolicy.canStatusModify(TODO, PENDING);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isFalse();
    }

    @DisplayName("완료 상태에서는 대기 상태를 제외하고 변경할 수 있다.")
    @Test
    void ifStatusCompleted() {
        //given //when
        boolean result1 = firstStatusPolicy.canStatusModify(DONE, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(DONE, IN_PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(DONE, DONE);
        boolean result4 = firstStatusPolicy.canStatusModify(DONE, PENDING);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isFalse();
    }

    @DisplayName("대기 상태에서는 어떤 상태로든 변경할 수 있다.")
    @Test
    void ifStatusHold() {
        //given //when
        boolean result1 = firstStatusPolicy.canStatusModify(PENDING, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(PENDING, IN_PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(PENDING, DONE);
        boolean result4 = firstStatusPolicy.canStatusModify(PENDING, PENDING);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();
    }

}