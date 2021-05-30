package sb.rf.generalchat.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import sb.rf.generalchat.controller.authorization.LoginController;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DisplayName("Login controller working then")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getLoginPage() is working")
    class getLoginPageTests{
        @Test
        public  void return_login_page() throws Exception {
            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk());

        }
        @Test
        public  void return_then_with_wrong_url() throws Exception {
            mockMvc.perform(get("/login123452"))
                    .andExpect(status().isNotFound());

        }



    }
    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("loginFail() is working")
    class geLoginFailTests{
        @Test
        public  void then_fail_return_json() throws Exception {
            mockMvc.perform(get("/loginFail"))
                    .andDo(print())
                    .andExpect(status().isNonAuthoritativeInformation())
                    .andExpect(jsonPath("$",is("Неправильное имя пользователя или пароль")));
        }
    }


}
