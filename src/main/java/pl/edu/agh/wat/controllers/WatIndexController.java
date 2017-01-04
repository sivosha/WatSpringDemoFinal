package pl.edu.agh.wat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * When the request come, spring will move to template index and render it
 */
@Controller
public class WatIndexController {
    @RequestMapping("/")
    String watIndex(){
        return "index";
    }
}