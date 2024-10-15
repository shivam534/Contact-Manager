package com.SMS.Smart_Contact_Manager.Service;

import com.SMS.Smart_Contact_Manager.Entities.Contact;
import com.SMS.Smart_Contact_Manager.Entities.User;
import com.SMS.Smart_Contact_Manager.Repository.ContactRepository;
import com.SMS.Smart_Contact_Manager.Repository.UserRepository;
import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    HttpSession session;

    @Autowired
    HttpServletResponse response;

    public String register(User user){
        for (Contact contact : user.getContacts()) {
            contact.setUser(user);  // Set the user reference in each contact
        }
        userRepository.save(user);
        return "Saved";
    }

    public List<User> getusers() {
       return userRepository.findAll();
    }

    public String addContact(String email, Contact contact) {
        try{
            User user = findUser(email);
            user.getContacts().add(contact);
            contact.setUser(user);
            userRepository.save(user);
            return "Updated";
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return e.getMessage();
        }
    }

    public User findUser(String email){
        return userRepository.findByEmail(email);
    }

    public List<Contact> getcontact(String email) {
        return userRepository.findByEmail(email).getContacts();
    }

    public String updateprofile(String email, User user) {
         User existinguser = findUser(email);
         existinguser.setAbout(user.getAbout());
         existinguser.setImaageUrl(user.getImaageUrl());
         existinguser.setName(user.getName());
         userRepository.save(existinguser);
         return "Updated";
    }

    public List<User> showuserwithrole(String role) {
        return userRepository.findAllByRole("ROLE_"+role.toUpperCase());
    }

    public List<Contact> findcontactbymail(String useremail, String contactemail) {
        User user = userRepository.findByEmail(useremail);
        return contactRepository.findByUser_idAndEmail(user.getId(),contactemail);
    }

    public String verfy(User user) {
        try{
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            if(authentication.isAuthenticated())
            {
                session.setAttribute("email",user.getEmail());
                return jwtService.generateToke(user.getEmail());
            }
            else return "Invalid email or password";
        }catch (UsernameNotFoundException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return e.getMessage();
        }catch (BadCredentialsException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return e.getMessage();
        } catch (DisabledException | AccountExpiredException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return e.getMessage();
        } catch (LockedException e){
            response.setStatus(423); // locked
            return e.getMessage();
        } catch (AuthenticationServiceException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return e.getMessage();
        }

    }


    // enable/disable User

    public User toggleEnable(String email) throws UsernameNotFoundException {
        User user = findUser(email);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        return user;
    }

    public User toggleLock(String email) {
        User user = findUser(email);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        user.setAccountNonLocked(!user.isAccountNonLocked());
        userRepository.save(user);
        return user;
    }
}

