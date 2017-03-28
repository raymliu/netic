package com.rays.service;

import com.rays.dto.User;
import com.rays.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hand on 2017/3/24.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository ;

    public User saveUser(User user){
        User reuser = userRepository.save(user);
        return reuser ;
    }

    public List<User> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users ;
    }

    public User findOne(Integer id){
        User user= userRepository.findOne(id);
        return user ;
    }

    public User UpdateUser(User user){
        User newUser = userRepository.save(user);
        return  newUser ;
    }

    public void deleteUser(Integer id){
        userRepository.delete(id);
    }
}
