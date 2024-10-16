package com.intuit.bidding_system.controller;

import com.intuit.bidding_system.entity.Category;
import com.intuit.bidding_system.entity.Product;
import com.intuit.bidding_system.exceptions.ProductRegistrationFailureException;
import com.intuit.bidding_system.model.request.ProductPayload;
import com.intuit.bidding_system.model.response.RegistrationResponse;
import com.intuit.bidding_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "registerProduct", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegistrationResponse> registerProduct(@RequestBody final ProductPayload productPayload)
            throws ProductRegistrationFailureException {
        final var registrationResponse = productService.registerProduct(productPayload);

        return ResponseEntity.ok()
            .body(registrationResponse);
    }

    @GetMapping(value = "browseByCategory/{category}")
    public List<Product> browseByCategory(final @PathVariable Category category) {
        return productService.getProductsByCategory(category);
    }
}
