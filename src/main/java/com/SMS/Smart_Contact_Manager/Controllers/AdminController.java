package com.SMS.Smart_Contact_Manager.Controllers;


import com.SMS.Smart_Contact_Manager.Entities.User;
import com.SMS.Smart_Contact_Manager.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;



    @GetMapping("/getusers")
    public List<User> get(){
        return userService.getusers();
    }

    @GetMapping("/{role}/show-users")
    public ResponseEntity<?> showuserwithrole(@PathVariable String role, Model model){
        List<User> userlist = userService.showuserwithrole(role);
        if(userlist == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content to display");
        model.addAttribute("no. of users",userlist.size());
        model.addAttribute("users",userlist);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @PostMapping("/enable-user/{email}")
    public User enableuser(@PathVariable String email){
        return userService.toggleEnable(email);
    }

    @PostMapping("/lock-user/{email}")
    public User lockeuser(@PathVariable String email){
        return userService.toggleLock(email);
    }


}
