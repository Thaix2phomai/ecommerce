package vn.com.techmaster.wineshopping_project.service;

import vn.com.techmaster.wineshopping_project.model.User;

import java.util.Optional;

public interface UserService {
    public User login(String email, String password);
    public boolean logout(String email);
//
    public User addUser(String fullname, String email,String phone, String password);
//    public Boolean activateUser(String activation_code);
//
//    public Boolean updatePassword(String email, String password);
//    public Boolean updateEmail(String email, String newEmail);
//
//    public Optional<User> findByEmail(String email);
//    public User findById(String id);
    public void checkValidate(String code);


//    public boolean isEmailExist(String email);
//
    public User updateNewPassword(String email);
}
