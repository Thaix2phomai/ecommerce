package vn.com.techmaster.wineshopping_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.com.techmaster.wineshopping_project.dto.UserDTO;
import vn.com.techmaster.wineshopping_project.model.Product;
import vn.com.techmaster.wineshopping_project.repository.ProductRepo;
import vn.com.techmaster.wineshopping_project.request.OrderRequest;
import vn.com.techmaster.wineshopping_project.request.ProductRequest;
import vn.com.techmaster.wineshopping_project.request.SearchRequest;
import vn.com.techmaster.wineshopping_project.service.CartService;
import vn.com.techmaster.wineshopping_project.service.ProductService;
import vn.com.techmaster.wineshopping_project.service.StorageService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String listAllProducts(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("searchRequest", new SearchRequest("", null));
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("cartCount", cartService.countItemInCart(session));

        return "homepage";
    }

    @GetMapping("/search")
    public String searchByName(@ModelAttribute("searchRequest") SearchRequest searchRequest, Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("products", productService.filterProduct(searchRequest));
        return "homepage";
    }


    @GetMapping(value = "/delete/{id}")
    public String deleteProductByID(@PathVariable String id, HttpSession session, Model model) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        productService.deleteById(id);
        return "redirect:/product";
    }


    @GetMapping(value = "/add")
    public String addProductForm(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("product", new ProductRequest("", "", "", "", null, 0, null, null));
        return "product_add";
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public String addProduct(@Valid @ModelAttribute("product") ProductRequest productRequest,
                             BindingResult result) throws IOException {
        //     UserDTO userDTO = (UserDTO) session.getAttribute("user");
        // System.out.println("Session ID: " + session.getId());
        // model.addAttribute("user", userDTO);
        if (productRequest.getLogo().getOriginalFilename().isEmpty()) {
            result.addError(new FieldError("product", "logo", "Logo is required"));
        }

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "product_add";
        }

        // Thêm vào cơ sở dữ liệu
        Product product = productService.add(Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).category(productRequest.getCategory()).build());

        // Lưu logo vào ổ cứng
        try {
            String logoFileName = storageService.saveFile(productRequest.getLogo(), product.getId());
            productService.updateLogo(product.getId(), logoFileName);
        } catch (IOException e) {
            // Nếu lưu file bị lỗi thì hãy xoá bản ghi
            productService.deleteById(product.getId());
            e.printStackTrace();
            return "product_add";
        }
        return "redirect:/product";
    }


    @GetMapping(value = "/edit/{id}")
    public String editProductId(Model model, @PathVariable("id") String id, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            Product currentProduct = product.get();
            model.addAttribute("productReq", new ProductRequest(
                    currentProduct.getId(),
                    currentProduct.getName(),
                    currentProduct.getDescription(),
                    currentProduct.getImage(),
                    null,
                    currentProduct.getQuantity(),
                    currentProduct.getPrice(),
                    currentProduct.getCategory()));
            model.addAttribute("product", currentProduct);
        }
        return "product_edit";
    }

    @PostMapping(value = "/edit", consumes = {"multipart/form-data"})
    public String editProduct(@Valid @ModelAttribute("productReq") ProductRequest productRequest,
                              BindingResult result,
                              Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "product_edit";
        }
        String logoFileName = null;

        // Cập nhật logo vào ổ cứng
        if (!productRequest.getLogo().getOriginalFilename().isEmpty()) {
            try {
                logoFileName = storageService.saveFile(productRequest.getLogo(), productRequest.getId());
                // employerRepo.updateLogo(employerRequest.id(), logoFileName);
            } catch (IOException e) {
                // Nếu lưu file bị lỗi thì hãy xoá bản ghi Employer
                productService.deleteById(productRequest.getId());
                e.printStackTrace();
                return "product_edit";
            }
        }
        // Cập nhật lại Product
        productService.edit(Product.builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .image(logoFileName == null ? productRequest.getLogo_path() : logoFileName)
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .build());

        return "redirect:/product";
    }

    @GetMapping("/buy/{id}")
    public String addToCart(Model model, HttpSession httpSession, @PathVariable(name = "id") String id) {
        cartService.addToCart(id, httpSession);
//        Cart cart = (Cart) httpSession.getAttribute("CART");
//        model.addAttribute("cart", cart );
        return "redirect:/product";
        }


    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        model.addAttribute("cart", cartService.getCart(session));
        return "checkout";
    }

    @GetMapping("/order")
    public String orderDetail (Model model){
        model.addAttribute("orderRequest", new OrderRequest("", "", "",null));
        return "order";
    }

    @PostMapping("/order")
    public String orderAction (@Valid @ModelAttribute("orderRequest") OrderRequest orderRequest){


        return "redirect:/product";
    }


}
