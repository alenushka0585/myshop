package de.telran.myshop.controllers;

import de.telran.myshop.entity.Product;
import de.telran.myshop.repository.ProductsRepository;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductsController {

    // spring boot при создании ProductsController вначале создаст экземпляр
    //ProductsRepository и автоматически добавит его сюда в виде зависимости

    @Autowired
    private ProductsRepository repo;

    @PutMapping("/prod")
    public Product create(
            @RequestBody
            @Parameter(name = "product", description = "Product to create or update", required = true)
            Product product
    ) {
        return repo.save(product);
    }

    @GetMapping("/prod")
    public Iterable<Product> getAll(){
        return repo.findAll();
    }

    @GetMapping("/prod/{id}")
    public Optional<Product> getAll(
            @PathVariable(name="id",required = true)
            Long id
    ){
        return repo.findById(id);
    }

    @DeleteMapping("/prod/{id}")
    public void deleteById(
            @PathVariable(name="id",required = true)
            Long id
    ){
        repo.deleteById(id);
    }

    @GetMapping("/active")
    public Iterable<Product> getByActive(
            @RequestParam (name = "active") boolean active
    ){
        return repo.findByIsActive(active);
    }
}
