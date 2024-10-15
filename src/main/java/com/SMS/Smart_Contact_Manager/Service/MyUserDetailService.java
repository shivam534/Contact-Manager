package com.SMS.Smart_Contact_Manager.Service;

import com.SMS.Smart_Contact_Manager.Entities.PrincipalUser;
import com.SMS.Smart_Contact_Manager.Entities.User;
import com.SMS.Smart_Contact_Manager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) throw new UsernameNotFoundException("Not found");
        return new PrincipalUser(user);
    }
}
