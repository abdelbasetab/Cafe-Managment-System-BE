package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.constents.CafeConstents;
import com.cafe.CafeManagmentApp.repository.CategoryRepository;
import com.cafe.CafeManagmentApp.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    //todo Add category

    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {





        }catch (Exception ex){

            ex.printStackTrace();

        }
        //if in dao something went wrong
        return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);



    }
}
