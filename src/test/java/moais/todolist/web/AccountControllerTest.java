package moais.todolist.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import moais.todolist.TestPrincipalDetailsService;
import moais.todolist.domain.account.AccountService;
import moais.todolist.domain.account.LoginAccount;
import moais.todolist.domain.account.dto.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Autowired
    private WebApplicationContext context;

    private final TestPrincipalDetailsService testUserDetailsService = new TestPrincipalDetailsService();

    private LoginAccount loginAccount;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print()).build();

        loginAccount = (LoginAccount) testUserDetailsService.loadUserByUsername(TestPrincipalDetailsService.USERNAME);
    }

    @DisplayName("신규 회원이 가입한다.")
    @Test
    void createAccount() throws Exception {
        //given
        AccountRequest request = AccountRequest.builder()
                .username("hello")
                .password("123")
                .build();

        //when //then
        mockMvc.perform(post("/account/signup")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("기존 회원이 탈퇴한다.")
    @Test
    void deleteAccount() throws Exception {
        //when //then
        mockMvc.perform(delete("/account/delete")
                        .with(user(loginAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("기존 회원이 아니면 탈퇴할 수 없다.")
    @Test
    void NotAccountNoDelete() throws Exception {
        //when //then
        mockMvc.perform(delete("/account/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}