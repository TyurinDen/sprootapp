package com.example.demo.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestController {

    @GetMapping(value = {"/user/socialNetworkTypes"})
    //@PreAuthorize("hasAnyAuthority('OWNER', 'ADMIN', 'USER')")
    public ResponseEntity<?> getSocialNetworkTypes() {
        List<String> socialProfileNames = Arrays.asList("Vk", "Facebook", "GitHub", "Google+");
        Map<Integer, String> socialTypeNames = new HashMap<>();
        int i = 1;
        for (String s: socialProfileNames) {
            socialTypeNames.put(i++, s);
        }
        return ResponseEntity.ok(socialTypeNames);
    }
}