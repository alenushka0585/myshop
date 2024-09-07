package de.telran.myshop.controllers;

import de.telran.myshop.entity.User;
import de.telran.myshop.errors.UserException;
import de.telran.myshop.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UsersController {

   // 1. Для User добавьте репозитори и контроллер,
   // в контроллере добавьте методы для создания пользователя,
   // просмотра списка пользователей
   // и пользователя по его идентификатору

    Logger logger = LoggerFactory.getLogger(this.getClass());
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
        logger.info("createUser login: {}, password: {}", userRequest.getLogins(), userRequest.getPassword());
        return usersRepository.save(userRequest);
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(
            @PathVariable Long id
    ) {
        if(!usersRepository.existsById(id)) {
            throw new UserException("User with " + id +" not found", id);
        }
        return usersRepository.findById(id);
    }
}
