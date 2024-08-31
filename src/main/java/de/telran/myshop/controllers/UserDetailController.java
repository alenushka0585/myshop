package de.telran.myshop.controllers;

import de.telran.myshop.entity.User;
import de.telran.myshop.entity.UserDetail;
import de.telran.myshop.repository.UserDetailRepository;
import de.telran.myshop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserDetailController {

//    2. Для UserDetail добавьте репозитори и контроллер,
//    в контроллере добавьте методы для создания UserDetail для  пользователя с выбранным идентификатором,
//    просмотра UserDetail по идентификатору.
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/users/{userId}/userDetails")
    public UserDetail createUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetail userDetailRequest
    ){
        User user = usersRepository.findById(userId).orElse(null);
        if(user == null){
            throw new IllegalArgumentException("No user with id " + userId);
        }
        userDetailRequest.setUser(user);
        return userDetailRepository.save(userDetailRequest);
    }


    @GetMapping("/userDetails/{userDetailsId}")
    public UserDetail getUserDetailById(
            @PathVariable Long userDetailsId
    ){
        UserDetail userDetail = userDetailRepository.findById(userDetailsId).orElse(null);
        if (userDetail == null){
            throw new IllegalArgumentException("No user details with id " + userDetailsId);
        }

        return userDetail;
    }
}
