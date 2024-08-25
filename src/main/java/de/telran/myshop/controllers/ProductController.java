package de.telran.myshop.controllers;

import de.telran.myshop.entity.Product;
import de.telran.myshop.entity.Result;
import de.telran.myshop.entity.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Controller // возвращаемое значение считаются названиями шаблонов из templates
@RestController // возвращается в виде json
// компонент спринг бут приложения, методы которого возвращают сущности
// компонент автоматически создается при старте приложения
// в количестве единственнго экхзэмпляра
// в него автоматически добавляются все нужные ему зависимости
// этот компонент автоматически предоставляется всем другим компонентам спринг бута
// которым эта зависимость нужна
public class ProductController {
    private List<Product> products = new ArrayList<>();

    public ProductController() {
        products.addAll(
                List.of(
                        Product.builder()
                                .id(1L)
                                .price(BigDecimal.valueOf(0.86))
                                .name("Coca-Cola 0.33 aluminium")
                                .isActive(false)
                                .build(),
                        Product.builder()
                                .id(2L)
                                .price(BigDecimal.valueOf(2.05))
                                .name("RedBull 0.5 aluminium")
                                .isActive(true)
                                .build(),
                        Product.builder()
                                .id(3L)
                                .price(BigDecimal.valueOf(3.16))
                                .name("Goat cheese")
                                .isActive(true)
                                .build(),
                        Product.builder()
                                .id(4L)
                                .price(BigDecimal.valueOf(1.12))
                                .name("Ravioli Neapolitano with tomatoes")
                                .isActive(true)
                                .build()
                )
        );
    }

    @Operation(
            summary = "Fetches all products",
            description = "Loads all data of all products"
    )
    // GET http://localhost:8080/products
    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return products;
    }


    //ResponseEntity = сущность + код возврата
    // GET http://localhost:8080/products

    @Operation(
            summary = "Fetch a product by id",
            description = "Loads a full product by its identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Invalid product id supplied"
            )
    }
    )
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable(value = "id")
            @Parameter(name = "id", description = "Product identifier", example = "2", required = true)
            Long id
    ) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Product with id= " + id + " not found"));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    //GET http://localhost:8080/p/8
    @GetMapping("/p/{id}")
    public Result getResultById(
            @PathVariable(value = "id") Long id
    ) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if(product != null) {
            return Result.builder().result(List.of(product)).build();
        } else {
            return Result.builder().error(Error.builder().description("No product with id " + id).build()).build();
        }
    }


    //DELETE http://localhost:8080/products/3
    // важно вернуть сигнал, что продукт был удален
    // важно вернуть исключение, если такого продукта нет
    @DeleteMapping("products/{id}")
    public ResponseEntity<Product> deleteProductById(
            @PathVariable(value = "id") Long id
    ) {
        boolean deleted = products.removeIf(p -> p.getId().equals(id));

        if (!deleted) {
            // если такого продукта нет, выбросить исключение как выше IllegalArgumentException
            throw new IllegalArgumentException("Product with id= " + id + " not found");
        } else {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
    }


    @PostMapping("/products")
    public Product createProduct(
            @RequestBody Product product //сущность берется из тела HTTP запроса
    ) {
        products.add(product);
        return product;
    }






    @Operation(
            summary = "Update or create a product by id",
            description = "Updates a product by its identifier or creates if not exist"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful operation, a product is created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }

            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, a product is updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }

            )
    }
    )
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> modifyProductById
            (@PathVariable(value = "id")
             @Parameter(name = "id", description = "Product identifier", example = "2", required = true)
             Long id,
             @RequestBody
             @Parameter(name = "Product", description = "A product to create or update", required = true)
             Product product
            ) {
        int position = IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId().equals(id)).findFirst().orElse(-1);
        if (position == -1) {
            // если нет продукта с таким идентификатором его нужно добавить в список и вернуть его + HttpStatus.CREATED
            products.add(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } else {
            // напишите код который изменяет продукт по идентификатору и возвращает его + HttpStatus.OK
            products.set(position, product);
            return ResponseEntity.ok(product);

        }
    }




    // интересует написать метод который вернет товары с определенным статусом active=true/active = false
// GET http://localhost:8080/products/getByStatus?active=true
// GET http://localhost:8080/products/getByStatus?active=false
    @GetMapping("/products/getByStatus")
    public Iterable<Product> getProductByStatus(
            @RequestParam(name = "active", defaultValue = "true") boolean active
    ) {
        // верните все продукты с нужным статусом
        return products.stream().filter(p -> p.isActive() == active).toList();
    }


    // GET http://localhost:8080/products/getByPrice?from=0.5&to=1.9
    /* */
    @GetMapping("/products/getByPrice")
    public Iterable<Product> getProductsWithPrice(
            @RequestParam(name = "from", defaultValue = "0.5") BigDecimal from,
            @RequestParam(name = "to", defaultValue = "1.9") BigDecimal to
    ) {
        return products.stream().filter(
                p -> p.getPrice().compareTo(from) >= 0 && p.getPrice().compareTo(to) <= 0
        ).toList();
    }

    //    Добавьте метод sortBy c чтобы можно было возвращать отсортированный список продуктов
//    GET http://localhost:8080/products/sortBy?by=price&how=desc
//    by - поле - price, name, id
//    how - desc, asc
    @GetMapping("products/sortBy")
    public Iterable<Product> sortBy(
            @RequestParam(name = "by", defaultValue = "name") String by,
            @RequestParam(name = "how", defaultValue = "asc") String how) {
        Comparator<Product> comparator = switch (by) {
            case "price" -> Comparator.comparing(Product::getPrice);
            case "name" -> Comparator.comparing(Product::getName);
            case "id" -> Comparator.comparing(Product::getId);
            default -> throw new IllegalArgumentException("Invalid sort field: " + by);
        };

        if ("desc".equalsIgnoreCase(how)) {
            comparator = comparator.reversed();
        } else if (!"asc".equalsIgnoreCase(how)) {
            throw new IllegalArgumentException("Invalid sort direction: " + how);
        }

        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }


    // получаем значение настроечного параметра из application.properties
    // с именем my.own.value а если значения там нет то по-умолчанию hello
    @Value("${my.own.value:hello}")
    private String myOwnValue;

}
