package vn.com.techmaster.wineshopping_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.dto.UserDTO;
import vn.com.techmaster.wineshopping_project.model.*;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CartServiceImple implements CartService {

    @Autowired
    ProductRepo productRepo;
    @Override
    public void addToCart(String id, HttpSession session) {
        HashMap<String, CartLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<String, CartLine>) rawCart;
        } else {
            cart = new HashMap<>();
        }

        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            CartLine cartLine = cart.get(id);
            if (cartLine == null) {
                cart.put(id, new CartLine(product.get(), 1));
            } else {
                cartLine.increaseByOne();
                cart.put(id, cartLine);
            }
        }

        session.setAttribute("CART", cart);
    }

    @Override
    public int countItemInCart(HttpSession session) {
        HashMap<Long, CartLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, CartLine>) rawCart;
            return cart.values().stream().mapToInt(CartLine::getCount).sum();
        } else {
            return 0;
        }
    }

    @Override
    public Cart getCart(HttpSession session) {
        HashMap<Long, CartLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, CartLine>) rawCart;
            return new Cart(
                    cart.values().stream().toList(),  //danh sách các mặt hàng mua
                    0.1f, //%Giảm giá
                    true   //Có tính thuế VAT không?
            );
        } else {
            return new Cart();
        }
    }

    public OrderDetail completeOrder (OrderRequest orderRequest, HttpSession session) {
            User user = (User) session.getAttribute("user");
            Cart cart = (Cart) session.getAttribute("CART");
            

    }


}


