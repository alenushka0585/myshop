package de.telran.myshop.controllers;

import de.telran.myshop.entity.User;
import de.telran.myshop.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

   // 1. Для User добавьте репозитори и контроллер,
   // в контроллере добавьте методы для создания пользователя,
   // просмотра списка пользователей
   // и пользователя по его идентификатору
    @Autowired
    private UsersRepository usersRepository;


    @PostMapping("/users")
    public User addUser(
            @RequestBody
            @Parameter(
                    name = "user",
                    description = "user to create",
                    required = true

            ) User userRequest
    ){
        return usersRepository.save(userRequest);
    }

    @GetMapping("/users")
    public Iterable<User> getAll(){
        return usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getById(
            @PathVariable Long id
    ){
        User user = usersRepository.findById(id).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("No user with id " + id);
        }

        return user;
    }
}
