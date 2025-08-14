package com.hexaware.careercrafter.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/*
 * REST controller for fetching details of authenticated user.
 */


@RestController
@RequestMapping("/api/auth")
public class AuthUserController {

    @GetMapping("/me")
    public String me(Authentication auth) {
        return auth == null ? "anonymous" : auth.getName();
    }
}
