package moais.todolist.domain.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static moais.todolist.domain.todo.TodoStatus.COMPLETED;
import static moais.todolist.domain.todo.TodoStatus.HOLD;
import static moais.todolist.domain.todo.TodoStatus.PROGRESS;
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
        boolean result1 = firstStatusPolicy.canStatusModify(TODO, HOLD);
        boolean result2 = firstStatusPolicy.canStatusModify(PROGRESS, HOLD);
        boolean result3 = firstStatusPolicy.canStatusModify(COMPLETED, HOLD);
        boolean result4 = firstStatusPolicy.canStatusModify(HOLD, HOLD);

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
        boolean result1 = firstStatusPolicy.canStatusModify(PROGRESS, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(PROGRESS, PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(PROGRESS, COMPLETED);
        boolean result4 = firstStatusPolicy.canStatusModify(PROGRESS, HOLD);

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
        boolean result2 = firstStatusPolicy.canStatusModify(TODO, PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(TODO, COMPLETED);
        boolean result4 = firstStatusPolicy.canStatusModify(TODO, HOLD);

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
        boolean result1 = firstStatusPolicy.canStatusModify(COMPLETED, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(COMPLETED, PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(COMPLETED, COMPLETED);
        boolean result4 = firstStatusPolicy.canStatusModify(COMPLETED, HOLD);

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
        boolean result1 = firstStatusPolicy.canStatusModify(HOLD, TODO);
        boolean result2 = firstStatusPolicy.canStatusModify(HOLD, PROGRESS);
        boolean result3 = firstStatusPolicy.canStatusModify(HOLD, COMPLETED);
        boolean result4 = firstStatusPolicy.canStatusModify(HOLD, HOLD);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();
    }

}