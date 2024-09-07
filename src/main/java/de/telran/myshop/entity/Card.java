package de.telran.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Добавьте валидацию в класс Card что name должно быть не пустым
    // и длиной не менее 5 символов
    @NotEmpty(message = "Card's name should not be empty")
    @Pattern(regexp = "^.{5,}$", message = "Minimum card's name length is 5")
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY, //пока явно не обратимся к продуктам, они не будут загружаться из базы данных
            cascade = {
                    CascadeType.ALL, // как изменения карты должны отражаться на продукте (менять и в продукте автоматически)
                    CascadeType.MERGE
            },
            mappedBy = "cards" // поле в классе Product
    )
    @JsonIgnore //исключить перегрузку по кругу
    @ToString.Exclude // не использовать в генерации toString
    @EqualsAndHashCode.Exclude // не использовать поле для equals и hashcode
    private Set<Product> products = new HashSet<>();
}
