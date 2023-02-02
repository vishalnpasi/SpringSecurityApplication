package com.SpringSecurityApplication.controllers;

import com.SpringSecurityApplication.dto.AuthRequest;
import com.SpringSecurityApplication.service.JwtService;
import com.SpringSecurityApplication.service.UserService;
import com.SpringSecurityApplication.models.UserModel;
import com.SpringSecurityApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
/*@CrossOrigin(origins="*")*/
public class ApplicationTest {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    public ApplicationTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
//    @Bean
//    public WebMvcConfigurer configure() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/*").allowedOrigins("http://localhost:9090");
//            }
//        };
//    }
    /*@CrossOrigin(origins="http://localhost:9090")*/
    @GetMapping("/public")
    public ResponseEntity<String> publicApi(){
        return ResponseEntity.ok(" I am public");
    }
    @PostMapping("/user")
    public String createUser(@RequestBody UserModel userModel){
        return service.saveUser(userModel);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){

        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        }
        else {
            throw new UsernameNotFoundException("Invalid user request !");
        }
    }
}
