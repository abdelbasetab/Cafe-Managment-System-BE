package com.cafe.CafeManagmentApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer Object class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWrapper {

    private Integer id;

    private String name;

    private String contactNumber;

    private String email;

    private String password;

    private String status;




}
