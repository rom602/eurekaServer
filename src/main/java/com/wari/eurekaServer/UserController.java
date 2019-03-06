package com.wari.eurekaServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(path = "/uaa/v1")
public class UserController {

    private UserService2 userService2;

    private AuthService authService;

    @Autowired
    public UserController(UserService2 userService2, AuthService authService) {
        this.userService2 = userService2;
        this.authService = authService;
    }

    // <1>
    @RequestMapping(path = "/me")
    public ResponseEntity<User> me(Principal principal) throws Exception {
         return Optional.ofNullable(authService.getAuthenticatedUser(principal)) // <2>
                .map(p -> ResponseEntity.ok().body(userService2.getUserByPrincipal(p))) // <3>
                .orElse(new ResponseEntity<User>(HttpStatus.UNAUTHORIZED)); // <4>
    }
}
