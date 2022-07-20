package vn.com.techmaster.wineshopping_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.techmaster.wineshopping_project.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    private String id;

    private String fullname;

    private String email;

    private Role role;
}
