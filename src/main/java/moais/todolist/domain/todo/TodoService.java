package moais.todolist.domain.todo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.account.Account;
import moais.todolist.domain.account.AccountRepository;
import moais.todolist.domain.account.dto.AccountRequest;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Long save(String username, TodoAddRequest addRequest) {
        Account account = accountRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        return todoRepository.save(addRequest.toEntity(account)).getId();
    }

    public List<TodoResponse> findAll() {
        return todoRepository.findAll().stream()
                .map(TodoResponse::of)
                .collect(Collectors.toList());
    }

}


