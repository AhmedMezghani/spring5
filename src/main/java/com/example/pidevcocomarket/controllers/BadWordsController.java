package com.example.pidevcocomarket.controllers;


import com.example.pidevcocomarket.entities.BadWords;
import com.example.pidevcocomarket.interfaces.Iservice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/badwords")
public class BadWordsController {
    @Autowired
    Iservice is;

    @PostMapping("/add-badword")
    BadWords addbadword(@RequestBody BadWords badWords) {return is.addword(badWords);}






}
