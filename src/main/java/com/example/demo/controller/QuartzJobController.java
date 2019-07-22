package com.example.demo.controller;

import com.example.demo.service.QuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class QuartzJobController {

    @Autowired
    private QuartzJobService service;
    @GetMapping("/start")
    public Object startQuartz(@RequestParam(value = "param",required = false)String param) {
        return service.startQuartz(param);
    }

    @GetMapping("/pause")
    public Object pauseQuartz(@RequestParam(value = "param",required = false)String param) {
        return service.pause(param);
    }
    @PostMapping("/add")
    public Object addQuartz() {
        return service.addQuartz();
    }

    @DeleteMapping("/delete")
    public Object deleteQuartz() {
        return service.deleteQuartz();
    }
    @PutMapping("/update")
    public Object updateQuartz() {
        return service.updateQuartz();
    }
}
