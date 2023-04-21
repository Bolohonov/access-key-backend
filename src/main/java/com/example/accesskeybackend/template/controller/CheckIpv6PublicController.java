package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.template.service.CheckIpv6Service;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketException;

@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class CheckIpv6PublicController {

    private final CheckIpv6Service checkIpv6Service;

    @GetMapping("/checkIpv6Support")
    public ResponseEntity checkIpv6(
            @RequestParam String siteUrl
    ) {
        try {
            return ResponseEntity.ok().body(checkIpv6Service.checkIpv6(siteUrl));
        } catch(SocketException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
