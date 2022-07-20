package vn.com.techmaster.wineshopping_project.request;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.techmaster.wineshopping_project.model.PaymentType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @NotBlank(message = "phone cannot blank")
    private String phoneContact;

    @NotBlank(message = "address cannot blank")
    private String address;

    @NotBlank(message = "email cannot blank")
    @Email(message = "Invalid email")
    private String email;

    private PaymentType paymentType;
}
