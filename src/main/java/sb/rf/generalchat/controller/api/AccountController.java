package sb.rf.generalchat.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.RegistrationDto;
import sb.rf.generalchat.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    Converter<String, User> converter;
    @Autowired
    private UserService userService;

    @GetMapping("/account/{user_email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("user_email") String email) {

        return ResponseEntity.ok(userService.getUserByEmail(User.builder().email(email).build()));
    }

    @GetMapping("/account")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/account")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/account/{admin_prop}")
    public ResponseEntity<User> createAdmin(@PathVariable("admin_prop") String adminProp) {
        return ResponseEntity.ok(userService.addUser(converter.convert(adminProp)));

    }

    @PostMapping("/account/reg")
    public ResponseEntity<User> basicAddUser(RegistrationDto dto) {
        return ResponseEntity.ok(userService.addUser(dto.getUser()));
    }


    @ApiOperation(notes = "Not deleted user, just marked him as Deleted", value = "Delete user by email", response = Integer.class)
    @DeleteMapping("/account/{user_email}")
    public ResponseEntity<Integer> deleteUserByEmail(@PathVariable("user_email") String email) {


        return ResponseEntity.ok(userService.deleteUser(email));
    }

    @RequestMapping(value = "/account",
            produces = "application/json",
            method = RequestMethod.PUT)
    public ResponseEntity<User> updateUserById(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }
    @GetMapping("/dailyonline")
    public ResponseEntity<Integer> getDailyOnline(){
       return ResponseEntity.ok(userService.findCountOfDailyChatedUsers());
    }




}
