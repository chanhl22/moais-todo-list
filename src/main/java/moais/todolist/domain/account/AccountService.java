package moais.todolist.domain.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.account.dto.AccountRequest;
import moais.todolist.domain.exception.TodoException;
import moais.todolist.domain.todo.TodoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final TodoRepository todoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new LoginAccount(account);
    }

    @Transactional
    public Long createNew(AccountRequest accountRequest) {
        isExistUsername(accountRequest);

        return accountRepository.save(accountRequest.toEntity(passwordEncoder)).getId();
    }

    private void isExistUsername(AccountRequest accountRequest) {
        accountRepository.findByUsername(accountRequest.getUsername())
                .ifPresent(account -> {
                    throw new TodoException("이미 등록된 회원입니다.");
                });
    }

    @Transactional
    public void delete(Account account) {
        todoRepository.deleteByAccount(account);
        accountRepository.deleteById(account.getId());
    }
}


