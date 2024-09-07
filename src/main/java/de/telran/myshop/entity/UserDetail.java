package de.telran.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Email // проверка на email
    private String email;
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
