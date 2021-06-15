package sb.rf.generalchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sb.rf.generalchat.config.AppConfig;
import sb.rf.generalchat.config.LocalizationConfig;

import sb.rf.generalchat.security.config.SecurityBasicConfig;
/*import sb.rf.generalchat.security.config.StatelessSecurityConfig;*/
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.function.Function;

@Import({AppConfig.class, SecurityBasicConfig.class, LocalizationConfig.class})
@SpringBootApplication
@EnableSwagger2

public class GeneralchatApplication {


    @Bean
    public Docket swaggerConfig(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("sb.rf.generalchat.controller.api"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "General chat  API",
                "Simple api for my chat, you MUST be Admin to use that api",
                "1.0",
                "Free to use, but need token",
                new Contact("L@st_One Team","/someurlHERE","lst0neh3r0@gmail.com"),
                "API license here",
                "some licenseurl is here",
                Collections.emptyList()


        );
    }

    Function

    public static void main(String[] args) {


        Integer w1 = new Integer(2);
        Integer w2 = new Integer(2);
        if(w1.equals(w2)){
            System.out.println("equal");
        }else {
            System.out.println("not equal");
        }


        SpringApplication.run(GeneralchatApplication.class, args);




    }
    @Bean
    public PasswordEncoder bcryptpasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
