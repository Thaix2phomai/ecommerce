package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.model.Contact;
import vn.com.techmaster.wineshopping_project.repository.ContactRepo;
import vn.com.techmaster.wineshopping_project.request.ContactRequest;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;
    public List<Contact> getAllMessage(){
        return contactRepo.findAll();
    }

    public Contact addNewMessage(ContactRequest contactRequest){
        String id = UUID.randomUUID().toString();
        Contact newContact = Contact.builder()
                .id(id)
                .fullname(contactRequest.getFullname())
                .email(contactRequest.getEmail())
                .subject(contactRequest.getSubject())
                .message(contactRequest.getMessage())
                .build();

        contactRepo.save(newContact);
        return newContact;
    }


}