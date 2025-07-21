package com.ochwada.jwt_auth_lab.model;


import lombok.*;

import java.util.List;


/**
 * *******************************************************
 * Package: com.ochwada.jwt_auth_lab.model
 * File: User.java
 * Author: Ochwada
 * Date: Monday, 21.Jul.2025, 3:01 PM
 * Description: Represents a registered user in the Secure Joke Vault system.
 * Objective:
 * *******************************************************
 */

/**
 * A simple representation of a user with username, password, and roles.
 * This will be hardcoded instead of being stored in a database.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** Username of the user */
    private String id;

    /** Password in plain text (not recommended for real apps!) */
    private  String name;

    /** Roles assigned to the user (e.g., USER, ADMIN) from enum*/
    private List<Role> roles;
}
