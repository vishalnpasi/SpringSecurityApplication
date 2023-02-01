package com.SpringSecurityApplication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationTest {

    @GetMapping("/")
    public String home(){
        return ("<h1>Welcome</h1>");
    }
//    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/normal")
    public ResponseEntity<String> user(){
        return  ResponseEntity.ok(" Hello , I am normal User");
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return  ResponseEntity.ok("I am Admin");
    }
    @GetMapping("/public")
    public ResponseEntity<String> publicApi(){
        return ResponseEntity.ok(" I am public");
    }
}
