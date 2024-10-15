package com.SMS.Smart_Contact_Manager.Repository;

import com.SMS.Smart_Contact_Manager.Entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    default List<Contact> findAllByWork(String work) {
        return null;
    }

    List<Contact> findByUser_idAndEmail(long uid,String mail);

}
