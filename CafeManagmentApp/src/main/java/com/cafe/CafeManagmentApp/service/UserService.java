package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.constents.CafeConstents;
import com.cafe.CafeManagmentApp.dto.UserWrapper;
import com.cafe.CafeManagmentApp.exception.UserNotFoundException;
import com.cafe.CafeManagmentApp.model.User;
import com.cafe.CafeManagmentApp.repository.UserRepository;
import com.cafe.CafeManagmentApp.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    /**
     *
     * @return all users from Database
     */
    public ResponseEntity<List<UserWrapper>> getAllUser(String username) {

        try {
            customerUserDetailsService.loadUserByUsername(username);

            //Check if the currentUser an Admin or not and is an active account
            if (customerUserDetailsService.isAdmin() && customerUserDetailsService.isActivatAccount()) {
                List<UserWrapper> allUser = new ArrayList();

                List<User> users = new ArrayList<>();
                users = userRepository.findAll();

                // add user data to the DTO List
                users.forEach(user -> {
                    UserWrapper userDao = new UserWrapper(user.getId(), user.getName(), user.getContactNumber(), user.getEmail(), user.getPassword(), user.getStatus());
                    allUser.add(userDao);

                });
                return new ResponseEntity<>(allUser, HttpStatus.OK);
            }
            else {

                //User don't have the permission to access to this resource
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.FORBIDDEN);
            }



        }catch (Exception ex){

            ex.printStackTrace();
        }
        //if something goes wrong
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        try{

            if (verifySignUpMap(requestMap)){
                //check later if its work
                User user = userRepository.findByEmail(requestMap.get("email"));

                if (Objects.isNull(user)){
                    User userFromMapReq = getUserFromMap(requestMap);
                    //save in the database
                    userRepository.save(userFromMapReq);
                    return CafeUtils.getResponseEntity(CafeConstents.SUCCEFUL_REGISTERT,HttpStatus.OK);
                }
                else {
                    return CafeUtils.getResponseEntity(CafeConstents.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private User getUserFromMap(Map<String, String> requestMap){

        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");//default is false
        user.setRole("user");//default is user
        return user;

    }




    //verify if the Request Map has all the data that we need

    private boolean verifySignUpMap(Map<String, String> requestMap){

        if (requestMap.containsKey("name")
                && requestMap.containsKey("email")
                &&requestMap.containsKey("password")
                &&requestMap.containsKey("contactNumber")) {
            return true;
        }
        else {
            return false;
        }


    }



    //case1 : user and the account is not active
    //case2 : user and the account is  active
    //case3 : is  user not exists  or   something went wrong

    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{


            customerUserDetailsService.loadUserByUsername(requestMap.get("email"));

            if(!Objects.isNull(customerUserDetailsService.getUserDetails())){
                //case1
                if (customerUserDetailsService.isActivatAccount()){
                    if (customerUserDetailsService.getUserDetails().getPassword().equals(requestMap.get("password"))) {
                        return CafeUtils.getResponseEntity(CafeConstents.SUCCESSFUL_LOGIN,HttpStatus.ACCEPTED);
                    }else {
                        return CafeUtils.getResponseEntity(CafeConstents.WRONG_PASSWORD,HttpStatus.BAD_REQUEST);
                    }
                }

                //case2
                else{
                    return CafeUtils.getResponseEntity(CafeConstents.ACCOUNT_IS_NOT_ACTIVE,HttpStatus.UNAUTHORIZED);
                }
            }


        }

        catch (Exception ex ){
            ex.printStackTrace();

        }
        //case4
        return CafeUtils.getResponseEntity(CafeConstents.INVALID_DATA,HttpStatus.BAD_REQUEST);
    }



    public User getUserDetails(String email) throws UserNotFoundException {
      User userDetails = userRepository.findByEmail(email);
      if (!Objects.isNull(userDetails)){
          return userDetails;
      }
      else {
          throw new UserNotFoundException("User not found in the Database");
      }
    }


    //todo ckeck the methode and test it 
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            //Case 1 check if there is an  account with this data
            User loadUser = customerUserDetailsService.loadUserByUsername(requestMap.get("email"));



            if (!Objects.isNull(loadUser)){
                //case 2 check if the account active
                if (customerUserDetailsService.isActivatAccount()){

                    User updatedUser = loadUpdatedData(requestMap);
                    //unupdated parameter
                    updatedUser.setId(loadUser.getId());
                    updatedUser.setEmail(loadUser.getEmail());
                    updatedUser.setRole(loadUser.getRole());
                    updatedUser.setStatus(loadUser.getStatus());
                    //updatable parameter
                    //check which parameter has changed
                    if (Objects.isNull(updatedUser.getPassword())){
                        updatedUser.setPassword(loadUser.getPassword());
                    }
                    if (Objects.isNull(updatedUser.getContactNumber())){
                        updatedUser.setPassword(loadUser.getContactNumber());
                    }
                    if (Objects.isNull(updatedUser.getName())){
                        updatedUser.setPassword(loadUser.getName());
                    }
                    userRepository.save(updatedUser);

                    return CafeUtils.getResponseEntity(CafeConstents.UPDATED_SUCCESSFUL,HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity(CafeConstents.ACCOUNT_IS_NOT_ACTIVE,HttpStatus.OK);
                }

            }else{

                return  CafeUtils.getResponseEntity(CafeConstents.ACCOUNT_IS_NOT_EXISTS,HttpStatus.OK);

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }




    private User loadUpdatedData(Map<String,String> requestMap){
        User user = new User();
        if (!Objects.isNull(requestMap.get("email"))){
            user.setEmail(requestMap.get("email"));

        }
        if (!Objects.isNull(requestMap.get("password"))){
            user.setPassword(requestMap.get("password"));

        }
        if (!Objects.isNull(requestMap.get("name"))){
            user.setName(requestMap.get("name"));

        }
        if (!Objects.isNull(requestMap.get("contactNumber"))){
            user.setContactNumber(requestMap.get("contactNumber"));

        }

        return user;


    }

}
