package sb.rf.generalchat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sb.rf.generalchat.controller.authorization.LoginController;

import java.util.function.Function;
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeneralchatApplicationTests {

    @Autowired
    LoginController loginController;

    @Test
    void contextLoads() {
    }

}
