package com.rays.service;

import com.rays.dto.User;
import com.rays.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by hand on 2017/3/24.
 */
@Service
@Transactional
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
    @Cacheable(value = "UserManager",key = "#id.toString()")
    public User findOne(Integer id){
        User user= userRepository.findOne(id);
        return user ;
    }
    @CachePut(value = "UserManager",key = "#user.id.toString()")
    public User UpdateUser(User user){
        User newUser = userRepository.save(user);
        return  newUser ;
    }
    @CacheEvict(value="UserManager",key = "#id.toString()")
    public void deleteUser(Integer id){
        userRepository.delete(id);
    }
}
