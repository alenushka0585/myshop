package de.telran.myshop.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsException  extends IllegalArgumentException{
    private Long commentId;
    private String body;
}
