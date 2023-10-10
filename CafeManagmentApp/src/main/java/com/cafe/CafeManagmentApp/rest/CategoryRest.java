package com.cafe.CafeManagmentApp.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping(path = "/category")
public interface CategoryRest {



    @PostMapping(path ="/add")
    ResponseEntity<String> addNewCategory(@RequestBody Map<String, String> requestMap);




}
