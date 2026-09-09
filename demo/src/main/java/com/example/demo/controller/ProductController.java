package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/add";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        // ผูก FK กลับให้ทุก review ที่กรอกมาพร้อมฟอร์ม (กัน review ที่ไม่มีข้อมูลจริง)
        if (product.getReviews() != null) {
            product.getReviews().removeIf(r -> r.getReviewer() == null || r.getReviewer().isBlank());
            product.getReviews().forEach(r -> r.setProduct(product));
        }
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "สินค้าถูกบันทึกแล้ว");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/edit";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productService.updateProduct(id, product);
        redirectAttributes.addFlashAttribute("message", "อัปเดตข้อมูลสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/delete";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("message", "ลบสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }
}
