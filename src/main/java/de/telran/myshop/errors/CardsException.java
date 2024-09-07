package de.telran.myshop.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardsException extends IllegalArgumentException{
    private String message;
    private Long id;
}
