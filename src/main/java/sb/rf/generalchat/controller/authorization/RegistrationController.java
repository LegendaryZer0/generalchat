package sb.rf.generalchat.controller.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.RegistrationDto;
import sb.rf.generalchat.service.IMessageService;
import sb.rf.generalchat.service.IUserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {

    @Autowired
 /*   @Qualifier("userService")*/
    private IUserService service;
    @Autowired
    private IMessageService messageService;

    @PostMapping("/register")
    @ResponseBody
    public String  registration(HttpServletResponse response, @Valid @RequestBody RegistrationDto dto, BindingResult result, HttpServletRequest request){
        if(!result.hasErrors()) {
            /*if(!dto.getPassword().equals(dto.getConfirm())) {response.setStatus(200) ;return "<h1  style=\"color: red\">" + mass[0]+"<h1>";}fixme проверку на совпадение confirm сделать на фронте */
            log.debug("Started registration {}", dto);
            log.info("РЕГИСТРАЦИЯ: полученные пароль и логин{}, {}", dto.getPassword(), dto.getLogin());
            User user =service.addUser(dto.getUser());
            if (user==null) {
                return "please,reload page";
            } else {
                request.getSession().setAttribute("user", service.getUserByEmail(user));
                messageService.sendWelcomeMessage(user.getId());
                response.setStatus(302);
                return "Успешно";
            }

        }
        log.info(String.valueOf(result.getAllErrors()));

        return result.getFieldError().getDefaultMessage(); //todo затестить на клиенте, что и как приходит


    }
    @GetMapping("/registration")
    public ModelAndView getRegistration(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        log.info(request.getSession().getAttribute("_csrf")+ "Предпологаемый csrf токен");

        modelAndView.setViewName("Registration");
        return modelAndView;
    }
    @GetMapping("/confirmPage")
    public String getConfirmPage(){
        return "ConfirmEmail";
    }


}
