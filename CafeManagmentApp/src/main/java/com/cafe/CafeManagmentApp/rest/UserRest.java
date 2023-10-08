package com.cafe.CafeManagmentApp.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "user")
public interface UserRest {



    @PostMapping(path ="/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String,String> requestMap);

    @PostMapping(path ="/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> requestMap);
}
