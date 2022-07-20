package vn.com.techmaster.wineshopping_project;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.com.techmaster.wineshopping_project.hash.Hashing;
import vn.com.techmaster.wineshopping_project.model.*;
import vn.com.techmaster.wineshopping_project.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
public class WineshoppingProjectApplication implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartLineRepo cartLineRepo;


    @Autowired
    private Hashing hashing;


    public static void main(String[] args) {
        SpringApplication.run(WineshoppingProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder().id("u1").userName("Thái").hashPassword(hashing.hashPassword("Thai123")).email("Thai123@gmail.com").address("Hà Nội").phone("0989980116").role(Role.Admin).state(State.ACTIVE).build();
        User user2 = User.builder().id("u2").userName("Cường").hashPassword(hashing.hashPassword("Cuong123")).email("Cuong123@gmail.com").address("Hà Nội").phone("0989980116").role(Role.Customer).state(State.ACTIVE).build();

        userRepo.save(user1);
        userRepo.save(user2);


//        Cart cart1 = Cart.builder().id("c1").user(user1).rawTotal(25000L).build();
//        Cart cart2 = Cart.builder().id("c2").user(user2).rawTotal(15000L).build();
//
//        cartRepo.save(cart1);
//        cartRepo.save(cart2);

//
//        OrderDetail orderDetail1 = OrderDetail.builder().id("o1").address("10 Tran Hung Dao").paymentType(PaymentType.POSTPAID).user(user1).build();
//        OrderDetail orderDetail2 = OrderDetail.builder().id("o2").address("10 Tran Hung Dao").paymentType(PaymentType.POSTPAID).user(user2).build();
//
//        orderRepo.save(orderDetail1);
//        orderRepo.save(orderDetail2);



        Product product1 = Product.builder().id("p1").name("Jack Daniel").description("Ngon, phê").price(10000L).quantity(50).category(Category.Whyskey).image("jackdaniel.jpg").build();
        Product product2 = Product.builder().id("p2").name("Corona").description("Mượt, mát").price(15000L).quantity(20).category(Category.Beer).image("corona.jpg").build();
        Product product3 = Product.builder().id("p3").name("Trung pd").description("Béo, tròn").price(20000L).quantity(15).category(Category.Whyskey).image("jameson.jpg").build();
        Product product4 = Product.builder().id("p4").name("Tobaco").description("Ngon, ngậy").price(30000L).quantity(16).category(Category.Cigar).image("cigar5.jpg").build();
        Product product5 = Product.builder().id("p5").name("Macallan").description("Say, Thấm").price(16000L).quantity(17).category(Category.Whyskey).image("macallan.jpg").build();
        Product product6 = Product.builder().id("p6").name("Tequilla").description("Mất trí nhớ").price(18000L).quantity(18).category(Category.Tequila).image("tequilla.jpg").build();
        Product product7 = Product.builder().id("p7").name("Botanica").description("Nhẹ, êm").price(32000L).quantity(19).category(Category.Wine).image("wine.jpg").build();
        Product product8 = Product.builder().id("p8").name("Budweiser").description("Vui vẻ, sành điệu").price(40000L).quantity(20).category(Category.Beer).image("budweiser.jpg").build();

        productRepo.save(product1);
        productRepo.save(product2);
        productRepo.save(product3);
        productRepo.save(product4);
        productRepo.save(product5);
        productRepo.save(product6);
        productRepo.save(product7);
        productRepo.save(product8);

//        String id = UUID.randomUUID().toString();
//        Cart cart1 = Cart.builder()
//                .id(id)
//                .rawTotal(0L)
//                .build();
//
//        CartLine cartLine1 = CartLine.builder().product(product1).id("ca1")
//                .cart(cart1)
//                .count(1).build();
//        CartLine cartLine2 = CartLine.builder().product(product2).id("ca2")
//                .cart(cart1)
//                .count(2).build();
//
//
//
//
//        cart1.addToCart(cartLine1);
//        cart1.addToCart(cartLine2);
////        cartLine1.setCart(cart1);
////        cartLine2.setCart(cart1);
//
//        cartRepo.save(cart1);
//
//        cartLineRepo.save(cartLine1);
//        cartLineRepo.save(cartLine2);






//        OrderLine orderLine1 = OrderLine.builder().id("or1").count(1).product(product1).orderDetail(orderDetail1).build();
//        OrderLine orderLine2 = OrderLine.builder().id("or2").count(2).product(product2).orderDetail(orderDetail1).build();
//        OrderLine orderLine3 = OrderLine.builder().id("or3").count(1).product(product1).orderDetail(orderDetail2).build();
//
//        orderLineRepo.save(orderLine1);
//        orderLineRepo.save(orderLine2);
//        orderLineRepo.save(orderLine3);


//        List<OrderLine> all1 = new ArrayList<>();
//        all1.add(orderLine1);
//        all1.add(orderLine2);
//        List<OrderLine> all2 = new ArrayList<>();
//        all2.add(orderLine3);


        //nối product
//        List<CartLine> caa1 = new ArrayList<>();
//        caa1.add(cartLine1);
//        caa1.add(cartLine3);
//
//
//        List<CartLine> caa2 = new ArrayList<>();
//        caa1.add(cartLine2);
//
//        cartLineRepo.saveAll(caa1);
//        cartLineRepo.saveAll(caa2);

        //List<OrderLine> oo1 = new ArrayList<>();
       // oo1.add(orderLine1);
       // oo1.add(orderLine3);

       // List<OrderLine> oo2 = new ArrayList<>();
       // oo2.add(orderLine2);




       // productRepo.save(product1);
       // productRepo.save(product2);
          //     productRepo.save(product3);


//        Cart cart1 = new Cart("c1", arr1, user1);
//        Cart cart2 = new Cart("c2", arr2, user2);
//        Cart cart3 = new Cart("c3", arr3, user3);
        //      Cart cart3 = Cart.builder().id("c3").user(user3).rawTotal(35000L).build();

//
//

        //      cartRepo.save(cart3);


        //OrderDetail orderDetail1 = new OrderDetail("o1",al,1000,user1, "10 Tran Hung Dao", PaymentType.POSTPAID );
        // OrderDetail orderDetail2 = new OrderDetail("o2",all2,1000,user2, "15 Ly Thuong Kiet", PaymentType.POSTPAID );
        // OrderDetail orderDetail3 = new OrderDetail("o3",all3,1000,user3, "2 Trang Tien", PaymentType.POSTPAID );

      //  OrderDetail orderDetail1 = OrderDetail.builder().id("o1").orderLines(all1).address("10 Tran Hung Dao").paymentType(PaymentType.POSTPAID).orderLines(all1).build();
    //    OrderDetail orderDetail2 = OrderDetail.builder().id("o2").orderLines(all2).address("10 Tran Hung Dao").paymentType(PaymentType.POSTPAID).orderLines(all2).build();
//        OrderDetail orderDetail3 = OrderDetail.builder().id("o3").orderLines(all3).address("10 Tran Hung Dao").paymentType(PaymentType.POSTPAID).user(user3).build();
//
//
  //      orderRepo.save(orderDetail1);
//        orderRepo.save(orderDetail2);
//        orderRepo.save(orderDetail3);


    }
}



