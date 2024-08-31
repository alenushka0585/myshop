package de.telran.myshop.controllers;

import de.telran.myshop.entity.Product;
import de.telran.myshop.repository.ProductsRepository;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public Optional<Product> getAllById(
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

    @GetMapping("/price")
    public Iterable<Product> getByPrice(
            @RequestParam(name = "price")BigDecimal price
            ){
        return repo.getByPrice(price);
    }

    // GET http://localhost:8080/priceBetween?from=1.12&to=2.33
    // напишите метод контроллера priceBetween
    // вызовите внутри метода контроллера repo.getProductWithPriceBetween
    @GetMapping("/priceBetween")
    public Iterable<Product> priceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to
    ) {
        return repo.getProductWithPriceBetween(from, to);
    }
}
