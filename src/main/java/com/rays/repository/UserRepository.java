package com.rays.repository;

import com.rays.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hand on 2017/3/24.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
