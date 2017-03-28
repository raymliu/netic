package com.rays.web;

import com.rays.dto.User;
import com.rays.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hand on 2017/3/24.
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService ;

    @RequestMapping(value="/save",method =RequestMethod.POST)
    private User saveUser(User user){
        User saveUser = userService.saveUser(user);
        return  saveUser ;
    }

    @RequestMapping(value ="/update",method = RequestMethod.PUT )
    private User updateUser(User user){
        User updateUSer = userService.UpdateUser(user);
        return updateUSer ;
    }
    @GetMapping("/get/{id}")
    public User getOne(@PathVariable("id")Integer id ){
        User newUesr = userService.findOne(id);
        return newUesr ;
    }

    @GetMapping("/getAll")
    public List<User> getAllUser(){
        List<User> allUsers = userService.findAllUsers();
        return  allUsers ;

    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
    }

}
