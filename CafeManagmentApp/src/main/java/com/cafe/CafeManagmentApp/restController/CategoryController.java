package com.cafe.CafeManagmentApp.restController;

import com.cafe.CafeManagmentApp.constents.CafeConstents;
import com.cafe.CafeManagmentApp.rest.CategoryRest;
import com.cafe.CafeManagmentApp.service.CategoryService;
import com.cafe.CafeManagmentApp.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CategoryController implements CategoryRest {

    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {

        try {
            return categoryService.addNewCategory(requestMap);

        }catch (Exception ex){

            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
