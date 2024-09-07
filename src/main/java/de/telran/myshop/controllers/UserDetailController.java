package de.telran.myshop.controllers;

import de.telran.myshop.entity.User;
import de.telran.myshop.entity.UserDetail;
import de.telran.myshop.errors.UserDetailException;
import de.telran.myshop.errors.UserException;
import de.telran.myshop.repository.UserDetailRepository;
import de.telran.myshop.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserDetailController {
    //Добавьте логгинг в UserDetailsController чтобы при запуске каждого метода
    // в лог в уровнем INFO выводились название метода и значения всех входных параметров
    Logger logger = LoggerFactory.getLogger(this.getClass());

//    2. Для UserDetail добавьте репозитори и контроллер,
//    в контроллере добавьте методы для создания UserDetail для  пользователя с выбранным идентификатором,
//    просмотра UserDetail по идентификатору.
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/users/{userId}/details")
    public UserDetail createUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetail userDetailRequest
    ) {
        logger.info("createUserDetail, userId: {}, UserDetail: {}", userId, userDetailRequest);

        User user = usersRepository.findById(userId).orElseThrow(
                () -> new UserException("User with id " + userId + " not found", userId)
        );
        userDetailRequest.setUser(user);
        return userDetailRepository.save(userDetailRequest);
    }


    @GetMapping("/users/details/{id}")
    public UserDetail getUserDetailsById(
            @PathVariable Long id
    ) {
        logger.info("getUserDetailsById, userId: {}", id);
        UserDetail userDetail = userDetailRepository.findById(id).orElseThrow(
                () -> new UserDetailException("UserDetail with id " + id + " not found", id)
        );

        return userDetail;
    }
}
