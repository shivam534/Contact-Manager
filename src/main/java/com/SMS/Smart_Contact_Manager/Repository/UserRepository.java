package com.SMS.Smart_Contact_Manager.Repository;

import com.SMS.Smart_Contact_Manager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    List<User> findAllByRole(String role);
}
