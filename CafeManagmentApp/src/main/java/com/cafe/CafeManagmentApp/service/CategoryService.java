package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.constents.CafeConstents;
import com.cafe.CafeManagmentApp.model.Category;
import com.cafe.CafeManagmentApp.repository.CategoryRepository;
import com.cafe.CafeManagmentApp.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;


    //todo Add category

    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {

            if (!Objects.isNull(customerUserDetailsService.loadUserByUsername(requestMap.get("email")))){
                //only admin can add a new category and must be active
                if (customerUserDetailsService.isAdmin() && customerUserDetailsService.isActivatAccount()){
                    //validateId is false because we need just the gateory name hier
                    if(validateCategoryMap(requestMap,false)) {

                        Category category = new Category();
                        category.setName(requestMap.get("name"));
                        categoryRepository.save(category);
                        return CafeUtils.getResponseEntity("Add new Category",HttpStatus.CREATED);
                    }else {
                        return CafeUtils.getResponseEntity(CafeConstents.INVALID_DATA,HttpStatus.UNAUTHORIZED);
                    }


                }else{
                    return CafeUtils.getResponseEntity(CafeConstents.UNAUTHORISED_ACCESS,HttpStatus.UNAUTHORIZED);
                }
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }catch (Exception ex){

            ex.printStackTrace();

        }
        //if in dao something went wrong
        return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);



    }

    private boolean validateCategoryMap(Map<String, String> requestMap,boolean validateId) {
        if (requestMap.containsKey("name")){
            // for the update methode we need to have an id
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){//will generate a new id
                return true;
            }
        }
            return false;

    }
}
