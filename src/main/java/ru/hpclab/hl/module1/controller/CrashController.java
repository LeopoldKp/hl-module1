package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class CrashController {

    @PostMapping("/crash")
    public void crash() {
        System.exit(1);
    }
}