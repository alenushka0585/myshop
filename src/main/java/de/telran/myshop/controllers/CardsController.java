package de.telran.myshop.controllers;

import de.telran.myshop.entity.Card;
import de.telran.myshop.entity.Comment;
import de.telran.myshop.entity.Product;
import de.telran.myshop.errors.CardsException;
import de.telran.myshop.repository.CardsRepository;
import de.telran.myshop.repository.CommentsRepository;
import de.telran.myshop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CardsController {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    // GET http://localhost:8080/cards
    @GetMapping("/cards")
    public Iterable<Card> getAllCards(){
        return cardsRepository.findAll();
    }


    // GET http://localhost:8080/cards/2
    @GetMapping("/cards/{cardId}")
    public Card getCardById(
            @PathVariable Long cardId){
        Card card = cardsRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new CardsException("No Card with id " + cardId, cardId);
        }
        return card;
    }


    //какие продукты естьв карте
    @GetMapping("/cards/{cardId}/products")
    public Iterable<Product> getCardProducts(
    @PathVariable Long cardId)
    {
        Card card = cardsRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new CardsException("No Card with id " + cardId, cardId);
        }
        return card.getProducts();
    }

    // создадим карту для продукта
    // POST http://localhost:8080/products/{productId}/cards
    @PostMapping("/products/{productId}/cards")
    public Card addCard(
            @PathVariable Long productId,
            @RequestBody Card cardRequest
    ) {
        Product product = productsRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + productId + " not found");
        }
        Long cardId = cardRequest.getId();
        if(cardId != null && cardId != 0L) {
            Card existingCard = cardsRepository.findById(cardId).orElseThrow(
                    () -> new CardsException("Card with id " + cardId + " not found", cardId)
            );
            product.addCard(existingCard);
            productsRepository.save(product);
            return existingCard;
        }
        product.addCard(cardRequest);
        return cardsRepository.save(cardRequest);
    }

    @PostMapping("cards/{id}")
    public Card updateCard (
            @PathVariable Long id,
            @RequestBody Card cardRequest
    ){
        Card card = cardsRepository.findById(id).orElse(null);
        if (card == null) {
            throw new CardsException("Card with id " + id + " not found", id);
        }

        card.setName(cardRequest.getName());

        return cardsRepository.save(card);
    }


    // удаление карты по идентификатору
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Card> deleteCardById(
            @PathVariable Long id
    ) {
        Card card = cardsRepository.findById(id).orElse(null);
        if (card == null) {
            throw new CardsException("Card with id " + id + " not found", id);
        }
        // удалить эту карту из всех ее продуктов
        List<Product> products = new ArrayList<>(card.getProducts());
        products.forEach(
                p -> p.removeCard(card.getId())
        );
        // продукты сохранить
        productsRepository.saveAll(products);
        // удалить карту
        cardsRepository.delete(card);
        return ResponseEntity.noContent().build();
    }


    // DELETE http://locahost:8080/products/2/cards/1
    // удаление конкретного продукта из карты
    @DeleteMapping("/products/{productId}/cards/{cardId}")
    public ResponseEntity<Product> deleteCardFromProduct(
            @PathVariable Long productId,
            @PathVariable Long cardId
    ) {
        Card card = cardsRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new CardsException("Card with id " + cardId + " not found", cardId);
        }
        Product product = productsRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + productId + " not found");
        }

        product.removeCard(cardId);
        productsRepository.save(product);

        return ResponseEntity.noContent().build();
    }


    //Добавьте в CardController вызов этого метода,
    // чтобы для указанной карты возвращались все комменты
    // для всех ее товаров

    @GetMapping("/cards/products/comments/{cardId}")
    Iterable<Comment> findAllCommentsForAllProductsByCardId(
            @PathVariable Long cardId){
        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new CardsException("Card with id " + cardId + " is not found", cardId)
        );

        List<Product> products = new ArrayList<>(card.getProducts());
        List<Comment> comments = new ArrayList<>();
        products
                .forEach(product -> commentsRepository.findByProductId(product.getId())
                        .forEach(comments::add));

        return comments;
    }


}
