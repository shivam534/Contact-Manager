package com.SMS.Smart_Contact_Manager.Controllers;


import com.SMS.Smart_Contact_Manager.Entities.Contact;
import com.SMS.Smart_Contact_Manager.Entities.User;
import com.SMS.Smart_Contact_Manager.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class Mycontroller {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;
   // private String contactemail;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        if(userService.findUser(user.getEmail())!=null)
            return user.getEmail()+" already exist";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userService.register(user);
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody User user){
        Map<String, String> res = new HashMap<>();
        res.put("message",userService.verfy(user));
      return  res;
    }
    @PostMapping("/addcontact")
    public String add( @RequestBody Contact contact){
        return userService.addContact((String) session.getAttribute("email"),contact);
    }

    @GetMapping("/getcontacts")
    public ResponseEntity<?> getcontact(Model model){
        List<Contact> contacts = userService.getcontact((String) session.getAttribute("email"));
        model.addAttribute("nos. of contacts",contacts.size());
        model.addAttribute("Contacts",contacts);
        return ResponseEntity.status(HttpStatus.FOUND).body(model);
    }

    @PostMapping("/profile/updatedetails")
    public ResponseEntity<?> updateprofile(@RequestBody User user){
        String email = (String) session.getAttribute("email");
        if(userService.findUser(email)==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(email+" doesn't exist");
       return ResponseEntity.status(HttpStatus.OK).body(userService.updateprofile(email,user));
    }

    // search contact

    @GetMapping("/find-contact/{contactemail}")
    public ResponseEntity<?> findcontactbymail(@PathVariable String contactemail){
        List<Contact> contacts = userService.findcontactbymail((String) session.getAttribute("email"), contactemail);
        return ResponseEntity.ok().body(contacts);
    }




}
