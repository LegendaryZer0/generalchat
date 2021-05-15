package sb.rf.generalchat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.function.Function;
@WebAppConfiguration
@SpringBootTest
class GeneralchatApplicationTests {
    Function<Integer, Boolean> numberToBooleanMapper = o -> {
        if(o>7){
            return true;
        }
        else return false;
    };
    @Test
    void contextLoads() {
    }

}
