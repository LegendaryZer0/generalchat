package sb.rf.generalchat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

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
