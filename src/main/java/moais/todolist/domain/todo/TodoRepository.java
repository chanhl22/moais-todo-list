package moais.todolist.domain.todo;

import moais.todolist.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @EntityGraph(attributePaths = {"account"})
    Page<Todo> findByAccount(Account account, Pageable pageable);

    @EntityGraph(attributePaths = {"account"})
    Optional<Todo> findTop1ByAccountOrderByCreatedDateTimeDesc(Account account);

    Optional<Todo> findByAccountAndId(Account account, Long id);

    @Modifying
    @Query("delete from Todo t WHERE t.account = :account")
    void deleteByAccount(Account account);

    List<Todo> findByAccount(Account account);

}
