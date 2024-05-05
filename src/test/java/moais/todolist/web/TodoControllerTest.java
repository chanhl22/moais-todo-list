package moais.todolist.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import moais.todolist.TestPrincipalDetailsService;
import moais.todolist.domain.account.LoginAccount;
import moais.todolist.domain.todo.TodoService;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoUpdateRequest;
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

import static moais.todolist.domain.todo.TodoStatus.TODO;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

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

    @DisplayName("TODO를 등록한다.")
    @Test
    void createAccount() throws Exception {
        //given
        TodoAddRequest request = TodoAddRequest.builder()
                .content("todo")
                .status(TODO)
                .build();

        //when //then
        mockMvc.perform(post("/todo/save")
                        .with(user(loginAccount))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("회원이 아니면 등록할 수 없다.")
    @Test
    void NotAccountNoCreate() throws Exception {
        //given
        TodoAddRequest request = TodoAddRequest.builder()
                .content("todo")
                .status(TODO)
                .build();

        //when //then
        mockMvc.perform(post("/todo/save")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("회원의 TODO 리스트를 조회한다.")
    @Test
    void findTodos() throws Exception {
        //when //then
        mockMvc.perform(get("/todo/list")
                        .with(user(loginAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("회원이 아니면 TODO 리스트를 조회할 수 없다.")
    @Test
    void NotAccountNoFind() throws Exception {
        //when //then
        mockMvc.perform(get("/todo/list")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("회원의 가장 최근 TODO 리스트를 조회한다.")
    @Test
    void findLatest() throws Exception {
        //when //then
        mockMvc.perform(get("/todo/latest")
                        .with(user(loginAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("회원의 가장 최근 TODO 리스트를 조회한다.")
    @Test
    void NotAccountNoLatest() throws Exception {
        //when //then
        mockMvc.perform(get("/todo/latest")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("회원의 TODO 리스트를 수정한다.")
    @Test
    void updateTodo() throws Exception {
        //given
        TodoUpdateRequest request = TodoUpdateRequest.builder()
                .id(1L)
                .content("todo")
                .status(TODO)
                .build();

        //when //then
        mockMvc.perform(post("/todo/update")
                        .with(user(loginAccount))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("회원이 아니라면 TODO를 수정할 수 없다.")
    @Test
    void NotAccountNoUpdate() throws Exception {
        //given
        TodoUpdateRequest request = TodoUpdateRequest.builder()
                .content("todo")
                .status(TODO)
                .build();

        //when //then
        mockMvc.perform(post("/todo/update")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}