package de.telran.myshop.controllers;

import de.telran.myshop.entity.Comment;
import de.telran.myshop.entity.Product;
import de.telran.myshop.repository.CommentsRepository;
import de.telran.myshop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// по аналогии с ProductsRepository
// превратите в контроллер
// добавьте метод который возвратит все коменты
// GET http://localhost:8080/comments
@RestController
public class CommentsController {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/comments")
    public Iterable<Comment> getAllComments() {
        return commentsRepository.findAll();
    }

    // GET http://localhost:8080/products/1/comments
    // вернуть все комменты определенного товара
    @GetMapping("/products/{productId}/comments")
    public Iterable<Comment> getAllCommentsByProductId(
            @PathVariable Long productId
    ) {
        // проверить если ли продукт и если нет выбросить исключение
        if(!productsRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product " + productId + "not found");
        }
        // вернуть все комменты для этого продукта
        return commentsRepository.findByProductId(productId);
    }

    // GET http://localhost:8080/comments/1
    // вернуть комментарий по его идентификатору
    // выбросив исключение если такого комментария нет
    @GetMapping("/comments/{commentId}")
    public Comment getCommentById(
            @PathVariable Long commentId
    ) {
        Comment comment = commentsRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new IllegalArgumentException("No comment with id " + commentId);
        }
        return comment;
    }

    // добавим комментарий к определенному товару
    @PostMapping("/products/{productId}/comments")
    public Comment createComment(
            @PathVariable Long productId, //
            @RequestBody Comment commentRequest // новый комментарий
    ) {
        Product product = productsRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("No product with id " + productId);
        }
        commentRequest.setProduct(product);
        return commentsRepository.save(commentRequest);
    }


    //    1. Добавьте в контроллер комментариев удаление комментария по идентификатору -
    //    DELETE для /comments/{id}

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comment> deleteComment(
            @PathVariable Long id
    ){
        Comment comment = commentsRepository.findById(id).orElse(null);
        if (comment == null){
            throw new IllegalArgumentException("Comment with id " + id + " is not found");
        }
        commentsRepository.delete(comment);

        return ResponseEntity.noContent().build();
    }


    //   2. Добавьте изменение комментария по идентификатору -
    //   PUT для /comments/{id} c приемом json тела коммента для изменения
    @PostMapping("comments/{id}")
    public Comment updateComment(
           @PathVariable Long id,
           @RequestBody Comment commentRequest
    ){
        Comment comment = commentsRepository.findById(id).orElse(null);
        if (comment == null){
            throw new IllegalArgumentException("Comment with id " + id + "  is not found");
        }

        comment.setContent(commentRequest.getContent());

        return commentsRepository.save(comment);
    }
}
