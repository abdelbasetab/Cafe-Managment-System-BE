package com.cafe.CafeManagmentApp.repository;

import com.cafe.CafeManagmentApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
