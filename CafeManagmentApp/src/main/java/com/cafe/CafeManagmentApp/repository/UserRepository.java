package com.cafe.CafeManagmentApp.repository;

import com.cafe.CafeManagmentApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {



     User findByEmail(String email);
}
