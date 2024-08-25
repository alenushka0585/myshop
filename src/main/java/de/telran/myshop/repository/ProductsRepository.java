package de.telran.myshop.repository;

import de.telran.myshop.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// CrudRepository - поддерживает базовые методы -
// вернуть все записи, вернуть одну запись по ключу,
// удалить запись по ключу, создать запись

//параметризация - Entity, тип первичного ключа Entity
@Repository
public interface ProductsRepository extends CrudRepository<Product, Long> {
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Iterable<Product> findByIsActive(boolean isActive);


    //ДЗ метод getByPrice
}
