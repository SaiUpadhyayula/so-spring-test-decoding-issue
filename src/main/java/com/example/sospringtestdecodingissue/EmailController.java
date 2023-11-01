package com.example.sospringtestdecodingissue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@Slf4j
public class EmailController {

    @GetMapping
    public String inputEmail(@RequestParam String email) {
      log.info("Received input email {}", email);
        return email;
    }
}
