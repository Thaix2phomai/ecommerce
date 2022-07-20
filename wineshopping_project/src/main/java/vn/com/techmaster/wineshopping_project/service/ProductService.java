package vn.com.techmaster.wineshopping_project.service;

import vn.com.techmaster.wineshopping_project.model.Product;
import vn.com.techmaster.wineshopping_project.request.SearchRequest;

import javax.servlet.http.HttpSession;
import java.util.Collection;

public interface ProductService {

    public Collection<Product> getAll();
    public Object filterProduct(SearchRequest searchRequest);

    public void deleteById(String id);

    public Product add(Product product);

    public void edit(Product product);

    public void updateLogo(String id,String logo_path);

}
