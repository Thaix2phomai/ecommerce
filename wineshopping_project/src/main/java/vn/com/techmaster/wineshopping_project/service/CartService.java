package vn.com.techmaster.wineshopping_project.service;

import vn.com.techmaster.wineshopping_project.model.Cart;

import javax.servlet.http.HttpSession;

public interface CartService {
    public void addToCart(String id, HttpSession session);

    public int countItemInCart(HttpSession session);

    public Cart getCart(HttpSession session);
}
