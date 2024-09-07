package de.telran.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    //@Transient - если не хочу, чтобы колонка создавалась в таблице базы данных
    @Pattern(regexp = "^.{6,}$", message = "Minimum password length is 6")
    private String password;

    @NotBlank(message = "User login should not be blank")
    @NotEmpty(message = "User login should not be empty")
    private String logins;

    @OneToOne(mappedBy = "user") // не является необходимым, только если нужно из user видеть UserDetail
    private UserDetail userDetail;

    //ДЗ контроллер и методы
}
