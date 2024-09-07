package de.telran.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Content should not be empty")
    @Pattern(regexp = ".*a.*", message = "Content should contain 'a' letter")
    private String content;

    // один комент относится только к одному товару
    // но к одному товару может быть несколько коментов
    @ManyToOne
    @JoinColumn(
            name = "product_id", // в таблице comments
            referencedColumnName = "id", // в таблице products
            nullable = false // не может быть комментов не относящихся ни к одному товару
    )
    @JsonIgnore
    private Product product;

    public Comment(String content, Product product) {
        this.content = content;
        this.product = product;
    }
}
