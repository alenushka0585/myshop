package de.telran.myshop.repository;

import de.telran.myshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


// CrudRepository - поддерживает базовые методы -
// вернуть все записи, вернуть одну запись по ключу,
// удалить запись по ключу, создать запись

//JpaRepository - тоже самое что Crud + методы для педжинга и сортировка

//параметризация - Entity, тип первичного ключа Entity
@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Iterable<Product> findByIsActive(boolean isActive);


    //ДЗ метод getByPrice
    Iterable<Product> getByPrice(BigDecimal price);

    //найдем все товары с ценой в диапазоне
    // найдем все товары с ценой в диапазоне
    @Query(
            nativeQuery = true,
            value = "select * from products where " +
                    "product_price > :priceFrom and product_price < :priceTo"
    )
    List<Product> getProductWithPriceBetween(
            BigDecimal priceFrom,
            BigDecimal priceTo
    );

    // JPQL - платформенно-независимый язык запросов по мотивам sql
    // который используется библиотекой hibernate которая лежит в основе JPA
    // в JPQL используется название Entity  полей Entity а не колонок и таблиц БД
    @Query("select p from Product p where p.price > :price")
    List<Product> getProductsWithPriceGreater(
            BigDecimal price
    );
}
