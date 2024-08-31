package de.telran.myshop.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

// ORM - Object Relational Mapping -
// как поля класса будут храниться в колонках таблицы базы данных

//JPA - Java persistence ...
//Hibernate оттуда пришла JPA

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity // включает для класса возможность храниться в таблице базы данных
@Table(name = "products") // как будет называться таблица в бд - по умолчанию - как название класса
public class Product {
    @Id//поле будет первичным ключом в таблице базы данных
    @GeneratedValue(strategy = GenerationType.IDENTITY)// в таблице колонка будет автоинкрементной
    @Schema(name = "Product ID", example = "2") //для документирования для Swagger
    private Long id; // чтобы в дальнейшем база данных сама создала идендификатор

    @Column(name = "PRODUCT_NAME", length = 50, nullable = false, unique = true)
    @Schema(name = "Product name", example = "Pepsi-Cola 0.33")
    private String name;

    @Column(name = "PRODUCT_PRICE")
    @Schema(name = "Product price", example = "12.33")
    private BigDecimal price;//Double не точный, BigDecimal точное - всегда использовать с ценами

    @Column(name = "PRODUCT_IS_ACTIVE")
    @Schema(name = "Product active", example = "false", description = "Product is actively sold")
    private boolean isActive;


    @OneToMany(
            mappedBy = "product"
    )
    private Set<Comment> comments = new HashSet<>();



    @ManyToMany(
            fetch = FetchType.LAZY, // не загружать карты вместе с продуктами
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "product_card", //таблица-связка
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "card_id")
            }
    )
    private Set<Card> cards = new HashSet<>();

    public void addCard(Card card) {
        cards.add(card); // добавляем карту к продукту
        card.getProducts().add(this); // добавляем продукт в карту
    }

    public void removeCard(long cardId) {
        Card card = cards.stream()
                .filter(c -> c.getId().equals(cardId)).findFirst().orElse(null);
        if(card != null) {
            cards.remove(card); // удаляем карту из карт продукта
            card.getProducts().remove(this); // удаляем продукт из карты
        }
    }
}
