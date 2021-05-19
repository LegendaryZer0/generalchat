package sb.rf.generalchat.controller.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.YouTubeVideo;
import sb.rf.generalchat.model.YoutubeSearchCriteria;
import sb.rf.generalchat.model.dto.FirstMessageDto;
import sb.rf.generalchat.model.dto.UserChangeSettingsDto;
import sb.rf.generalchat.security.details.UserDetailsImpl;
import sb.rf.generalchat.service.UserService;
import sb.rf.generalchat.service.YouTubeService;
import sb.rf.generalchat.util.AuthenticationFacade;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller("ProfController")
@Slf4j
@RequestMapping("/user")
public class ProfileController {
    @Autowired
 /*   @Qualifier("userServiceImpl")*/
    private UserService userService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private YouTubeService youTubeService;


    @GetMapping("/selfProfile")

    public String getProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request, Principal principal){
        User user =userService.getUserByEmail(User.builder().email(userDetails.getUsername()).build());
        log.info("COMING PRINCIPALS {}",principal);
        log.info("Facade {}",authenticationFacade.getAuthentication().toString());
        log.info("details {}",userDetails.toString());
        request.getSession().setAttribute("user",user);
        model.addAttribute("messageForm", new FirstMessageDto());
        model.addAttribute("userSettingsForm", UserChangeSettingsDto.from(user));

        return "Profile";
    }

    @PostMapping("/usettings")
    public String changeUserSettings(HttpServletRequest request, @Valid UserChangeSettingsDto userSettingsForm, Model model, BindingResult bindingResult){
        log.info("userSettingsForm {}",userSettingsForm);
       /* model.addAttribute("messageForm", new FirstMessageDto());*/
        if(!bindingResult.hasErrors()) {
            User userInSession = (User) request.getSession().getAttribute("user");
            log.info("No errors");
            User user = userSettingsForm.getUser();
            log.info("User in changeSettings Controller{}", user);

            user = userService.updateUser(user);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                log.info("All went well, just giving the same profile page");
                return "redirect:/user/selfProfile";    //return Profile
            } else return "redirect:/user/selfProfile";
        }else {

            model.addAttribute("passNamesErrors",bindingResult.getAllErrors().stream().anyMatch(error->{
                if(Objects.requireNonNull(error.getCodes())[0].equals("userSettingsForm.ValidPassName")){
                    model.addAttribute("passNamesErrors",error.getDefaultMessage());
                }
                return  true;
            }));
            return "Profile";
        }

    }


    @ResponseBody
    @RequestMapping(value = "/getVideos", method= RequestMethod.GET)
    public List<YouTubeVideo> formSubmit(@Valid YoutubeSearchCriteria youtubeSearchCriteria, BindingResult bindingResult, Model model) {
        //check for errors
        if (bindingResult.hasErrors()) {
            return new ArrayList<YouTubeVideo>();
        }
        log.info("Gotted youtube search criteria {}",youtubeSearchCriteria.toString());
        log.info(youtubeSearchCriteria.getQueryTerm());
        //get the list of YouTube videos that match the search term
        List<YouTubeVideo> videos = youTubeService.fetchVideosByQuery(youtubeSearchCriteria.getQueryTerm());
        log.info("Founded videos  {}", videos);
        //get out
        return videos;
    }


}
