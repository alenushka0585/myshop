package de.telran.myshop.entity;

import jakarta.persistence.*;
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
    private String password;
    private String logins;

    @OneToOne(mappedBy = "user") // не является необходимым, только если нужно из user видеть UserDetail
    private UserDetail userDetail;

    //ДЗ контроллер и методы
}
