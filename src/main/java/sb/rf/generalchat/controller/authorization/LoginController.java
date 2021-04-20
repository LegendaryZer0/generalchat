package sb.rf.generalchat.controller.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sb.rf.generalchat.service.IUserService;


import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private IUserService userServiceImpl;

    @GetMapping("/login")
    public String getLogin() {
        log.info("Отдаю страницу логина");
        return "Login";
    }

    @GetMapping("/loginFail")
    @ResponseBody
    public String loginFail(HttpServletResponse response) {
        response.setStatus(203);
        return "Неправильное имя пользователя или пароль";
    }


/*
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request, HttpServletResponse response)  {


        response.setCharacterEncoding("UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user = userLoginDto.getUser();
        isSaved = userLoginDto.getIsChecked();
        log.info("USERLOGINDTO{}", userLoginDto.toString());

        User finUser = userServiceImpl.getUser(user.getEmail(), user.getPassword());

        log.info("Пользователь нажал Remember me{}", isSaved);

        if (finUser!=null && finUser.getEmail() != null) {
            String uuid = UUID.randomUUID().toString();
            cookie = new Cookie("Auth", uuid);

            if (userLoginDto.getIsChecked()) {
                cookie.setMaxAge(360 * 360 * 360);
            } else {
                cookie.setMaxAge(360 * 60 * 60);
            }
            response.addCookie(cookie);
            finUser.setTechnicalInfo(TechnicalInfo.builder().uuid(UUID.fromString(uuid)).build());
            userServiceImpl.updateUserUUID(finUser);
            request.getSession().setAttribute("user", finUser);


            response.setStatus(302);

        }
        return s;
    }*/


}
