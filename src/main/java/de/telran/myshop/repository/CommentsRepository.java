package de.telran.myshop.repository;

import de.telran.myshop.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findByProductId(Long productId);

    @Transactional // все изменеия таблиц БД выполняются в рамках транзакции
    @Modifying // метод изменяет таблицы БД
    void deleteByProductId(long productId);

    // как сделать чтобы

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from comments where product_id=:productId"
    )
    void deleteAllProductComments(long productId);
}
