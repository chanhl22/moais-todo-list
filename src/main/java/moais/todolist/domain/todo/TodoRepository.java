package moais.todolist.domain.todo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Override
    @EntityGraph(attributePaths = {"account"})
    List<Todo> findAll();

}
