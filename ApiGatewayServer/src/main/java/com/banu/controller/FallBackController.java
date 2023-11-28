package com.banu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/auth")
    public ResponseEntity<String> getFallBackAuth() {
        return ResponseEntity.ok("Auth micro servisi şuan hizmet veremiyor. Lütfen daha sonora tekrar deneyiniz");
    }

    @GetMapping("/product")
    public ResponseEntity<String> getFallBackProduct() {
        return ResponseEntity.ok("Product micro servisi şuan hizmet veremiyor. Lütfen daha sonora tekrar deneyiniz");
    }

    @GetMapping("/user")
    public ResponseEntity<String> getFallBackUser() {
        return ResponseEntity.ok("User micro servisi şuan hizmet veremiyor. Lütfen daha sonora tekrar deneyiniz");
    }
}
