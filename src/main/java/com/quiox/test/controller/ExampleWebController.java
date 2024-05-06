package com.quiox.test.controller;

import com.quiox.test.entity.ExampleEntity;
import com.quiox.test.projection.ExampleEntityProjection;
import com.quiox.test.service.ExampleEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import org.springframework.ui.Model;
@Controller
@RequestMapping("/api/example/web")
public class ExampleWebController {

    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "friend")
            String name, Model model) {

            model.addAttribute("name", name);

           return "views/example"; //view
    }


}
