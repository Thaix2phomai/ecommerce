package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.Util.RandomPassword;
import vn.com.techmaster.wineshopping_project.exception.UserException;
import vn.com.techmaster.wineshopping_project.hash.Hashing;
import vn.com.techmaster.wineshopping_project.model.Cart;
import vn.com.techmaster.wineshopping_project.model.Role;
import vn.com.techmaster.wineshopping_project.model.State;
import vn.com.techmaster.wineshopping_project.model.User;
import vn.com.techmaster.wineshopping_project.repository.UserRepo;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    Hashing hashing;

    @Autowired
    ActiveCodeService activeCodeService;

    @Autowired
    EmailService emailService;


    @Override
    public User login(String email, String password) {
        Optional<User> o_user = userRepo.findUsersByEmail(email);
        if (!o_user.isPresent()) {
            throw new UserException("User is not found");
        }

        User user = o_user.get();
        // User muốn login phải có trạng thái Active
        if (user.getState() != State.ACTIVE) {
            throw new UserException("User is not activated");
        }
        // Kiểm tra password
        if (hashing.validatePassword(password, user.getHashPassword())) {
            return user;
        } else {
            throw new UserException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String email) {
        return false;
    }

    @Override
    public User addUser(String fullname, String email, String phone, String password) {
        String id = UUID.randomUUID().toString();
        String cartId = UUID.randomUUID().toString();
        Cart cart = Cart.builder().id(cartId).build();
        User user = User.builder()
                .id(id)
                .userName(fullname)
                .email(email)
                .phone(phone)
                .hashPassword(hashing.hashPassword(password))
                .state(State.PENDING)
                .role(Role.Customer)
                .cartItem(cart)
                .build();
        if (user.getState().equals(State.PENDING)) {
            String regisCode = UUID.randomUUID().toString();
            activeCodeService.addCode(regisCode, id);
            try {
                emailService.sendEmail(email, regisCode);
            } catch (Exception e) {
                // activeCodeService.getAllActiveCode().remove(regisCode);
                userRepo.delete(user);
                throw new UserException("Địa chỉ email của bạn không tồn tại");
            }
        }
        userRepo.save(user);
        return user;
    }


    @Override
    public void checkValidate(String code) {
        User user = userRepo.findById(activeCodeService.getAllActiveCode().get(code)).get();
        user.setState(State.ACTIVE);
        userRepo.save(user);
        // activeCodeService.getAllActiveCode().remove(code);
    }

    @Override
    public User updateNewPassword(String email) {
        Optional<User> user = userRepo.findUsersByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User is not found");
        }
        RandomPassword rand = new RandomPassword();
        String newPassword = rand.randomPassword(8);
        User currentUser = user.get();
        currentUser.setHashPassword(hashing.hashPassword(newPassword));
        userRepo.save(currentUser);
        emailService.newPassword(email, newPassword);
        return currentUser;
    }

}
