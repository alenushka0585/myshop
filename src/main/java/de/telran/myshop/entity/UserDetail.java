package de.telran.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.telran.myshop.validation.ValidDob;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "details")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailId;

   // Добавьте валидацию email для UserDetail
    @Email // проверка на email
    private String email;

    //Напишите валидацию для dob в UserDetail чтобы дата рождения не была в будущем от текущего дня
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    @ValidDob(message = "Дата рождения не может быть в прошлом больше чем на 100 лет от текущего дня")
    private Date dob;
    private String tel;

    @OneToOne
    @JoinColumn
            (name = "user_id", // название колонки внешнего ключа)
                    referencedColumnName = "userId"
            )
    @JsonIgnore
    private User user;
}
