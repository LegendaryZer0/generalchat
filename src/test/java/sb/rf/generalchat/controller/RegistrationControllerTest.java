package sb.rf.generalchat.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.RegistrationDto;
import sb.rf.generalchat.service.UserService;


import java.util.Optional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
@AutoConfigureMockMvc
@DisplayName("Registration controller working then")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        when(userService.addUser(User.builder()
                .email("user1")
                .password("hashpaswd1")
                .build()))
                .thenReturn(User.builder()
                        .email("user1")
                        .password("hashpaswd1")
                        .build());

        when(userService.addUser(User.builder()
                .email("existedUser")
                .password("hashpaswd2")
                .build()))
                .thenReturn(null);

        when(userService.getUser("user1", "hashpaswd1"))
                .thenReturn(Optional.of(User.builder()
                        .email("user1")
                        .password("hashpaswd1")
                        .build()));
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("registration() is working")
    class RegistrationTest {
        @Test
         public void resturn_results_of_registration_with_wrong_email() throws Exception {
             mockMvc.perform(post("/register")
                     .with(csrf())
                     .accept(MediaType.APPLICATION_JSON)
                     .contentType(MediaType.APPLICATION_JSON_VALUE)
                     .content(objectMapper.writeValueAsString(RegistrationDto.builder()
                     .password("hashpaswd1")
                     .login("user1").build())))
                     .andExpect(jsonPath("$",is("wrong email")));
         }
    }
}
