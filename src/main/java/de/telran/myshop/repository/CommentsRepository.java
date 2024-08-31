package de.telran.myshop.repository;

import de.telran.myshop.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findByProductId(Long productId);
}
