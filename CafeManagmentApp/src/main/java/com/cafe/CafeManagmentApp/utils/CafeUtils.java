package com.cafe.CafeManagmentApp.utils;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * All Static Methode that will be used in more than one Class
 */

public class CafeUtils {



    //private we cannot create an object of the type CafeUtils
    private CafeUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMsg, HttpStatus httpstatus){

        return new ResponseEntity<>("{\"message\": \""+responseMsg +"\" }",httpstatus );
    }
}
