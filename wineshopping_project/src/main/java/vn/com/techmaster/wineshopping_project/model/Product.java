package vn.com.techmaster.wineshopping_project.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private String id;


    private String name;

    private String description;

    private String image;

    private int quantity;

    private Long price;

    private Category category;

    private LocalDateTime update_at;

    private LocalDateTime create_at;



    @OneToMany( mappedBy = "product",fetch = FetchType.LAZY)
    private List<CartLine> cartLines;


    @OneToMany( mappedBy = "product",fetch = FetchType.LAZY)
    private List<OrderLine> orderLines;




}
