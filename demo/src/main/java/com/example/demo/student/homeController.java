package com.example.demo.student;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class homeController {


    @RequestMapping("/status")
    public ModelAndView status () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


        @RequestMapping("/index")

        public String welcome() {
            return "index";
        }
    }



