package com.MobileStore.demo.controller;

import com.MobileStore.demo.entity.Product;
import com.MobileStore.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 1. READ
    @GetMapping
    public String listProducts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByName(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        return "admin/AdminPage";
    }



    // 2. CREATE
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/ProductForm";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product, RedirectAttributes ra) {
        productService.saveProduct(product);
        ra.addFlashAttribute("message", "Lưu sản phẩm thành công!");
        return "redirect:/admin/products";
    }

    // 3. UPDATE
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/ProductForm";
    }

    // 4. DELETE
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes ra) {
        productService.deleteProduct(id);
        ra.addFlashAttribute("message", "Đã xóa sản phẩm thành công!");
        return "redirect:/admin/products";
    }
}