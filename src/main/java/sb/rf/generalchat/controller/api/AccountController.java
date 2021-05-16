package sb.rf.generalchat.controller.api;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.RegistrationDto;
import sb.rf.generalchat.service.UserService;

import javax.persistence.criteria.Expression;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    Converter<String,User> converter;
    @Autowired
    private UserService userService;
    @GetMapping("/account/{user_email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("user_email") String email){

        return ResponseEntity.ok(userService.getUserByEmail(User.builder().email(email).build()));
    }
    @GetMapping("/account")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/account")
    public ResponseEntity<User> createUser(@RequestBody User user){

        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/account/{admin_prop}")
    public ResponseEntity<User> createAdmin(@PathVariable("admin_prop") String adminProp){
        return ResponseEntity.ok(userService.addUser(converter.convert(adminProp)));

    }

    @PostMapping("/account/{email}/{password}")
    public ResponseEntity<User> addUser(@PathVariable("email") String email,@PathVariable("password") String password){
        return ResponseEntity.ok(userService.addUser(User.builder().email(email).password(password).nickname(email).role(User.Role.USER).state(User.State.FROZEN).build()));
    }

    @PostMapping("/account/reg")
    public ResponseEntity<User> basicAddUser(RegistrationDto dto){
        return ResponseEntity.ok(userService.addUser(dto.getUser()));
    }



    @ApiOperation(notes = "Not deleted user, just marked him as Deleted" ,value = "Delete user by email",response = Integer.class)
    @DeleteMapping("/account/{user_email}")
    public ResponseEntity<Integer> deleteUserByEmail(@PathVariable("user_email") String email){
        

        return ResponseEntity.ok(userService.deleteUser(email));
    }

    @RequestMapping(value = "/account",
            produces = "application/json",
            method=RequestMethod.PUT)
    public ResponseEntity<User> updateUserById(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }





}
