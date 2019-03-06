package com.wari.eurekaServer;

import com.wari.eurekaServer.User;
import com.wari.eurekaServer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.RequestEntity.get;

@Service
public class UserService2 {

    private final UserRepository userRepository;

    @Autowired
    public UserService2(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User getUserByPrincipal(Principal principal) {
        // <1>
        return Optional.ofNullable(principal)
                .map(p -> userRepository.findUserByUsername(p.getName())).orElse(null);
    }
}