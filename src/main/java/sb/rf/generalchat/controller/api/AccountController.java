package sb.rf.generalchat.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.RegistrationDto;
import sb.rf.generalchat.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private IUserService userService;
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

    @PostMapping("/account/{email}/{password}")
    public ResponseEntity<User> addUser(@PathVariable("email") String email,@PathVariable("password") String password){
        return ResponseEntity.ok(userService.addUser(User.builder().email(email).password(password).nickname(email).role(User.Role.USER).state(User.State.FROZEN).build()));
    }

    @PostMapping("/account/reg")
    public ResponseEntity<User> basicAddUser(RegistrationDto dto){
        return ResponseEntity.ok(userService.addUser(dto.getUser()));
    }



}
