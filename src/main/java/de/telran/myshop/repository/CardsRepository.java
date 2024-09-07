package de.telran.myshop.repository;

import de.telran.myshop.entity.Card;
import de.telran.myshop.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends CrudRepository<Card, Long> {
    // Добавьте в CardRepository метод, который вернет все комменты
    // для всех товаров в карте

    @Query(
            nativeQuery = true,
            value = "SELECT c FROM comments c WHERE c.product IN (SELECT p FROM cards card JOIN card.products p WHERE card.id = :cardId)")
    Iterable<Comment> findAllCommentsForAllProductsByCardId(long cardId);
}
